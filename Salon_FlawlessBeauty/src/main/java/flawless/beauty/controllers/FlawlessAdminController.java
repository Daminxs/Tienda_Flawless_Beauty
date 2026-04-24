
package flawless.beauty.controllers;

/**
 *
 * @author Damin
 */

import flawless.beauty.domain.*;
import flawless.beauty.repository.*;
import flawless.beauty.service.*;
import flawless.beauty.service.FlawlessPromocionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class FlawlessAdminController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessRolRepository rolRepository;

    @Autowired
    private FlawlessProductoService productoService;

    @Autowired
    private FlawlessServicioService servicioService;

    @Autowired
    private FlawlessCategoriaService categoriaService;

    @Autowired
    private FlawlessCitaService citaService;

    @Autowired
    private FlawlessReservaService reservaService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private FlawlessPromocionService promocionService;

    // PANEL PRINCIPAL
    @GetMapping
    public String panel() {
        return "salonpaneladmin/indexadmin";
    }

    // USUARIOS
    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "salonpaneladmin/usuarios";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {

        FlawlessUsuario usuario = new FlawlessUsuario();
        usuario.setRoles(new java.util.ArrayList<>());

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());

        return "salonpaneladmin/editarUsuario";
    }

    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {

        FlawlessUsuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario == null) {
            return "redirect:/admin/usuarios";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());

        return "salonpaneladmin/editarUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(
            FlawlessUsuario usuario,
            @RequestParam(value = "roles", required = false) java.util.List<Long> rolesIds) {

        FlawlessUsuario actual = null;

        if (usuario.getIdUsuario() != null) {
            actual = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        }

        if (actual == null) {
            actual = new FlawlessUsuario();

            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                return "redirect:/admin/usuarios/nuevo";
            }
        }

        actual.setNombre(usuario.getNombre());
        actual.setCorreo(usuario.getCorreo());
        actual.setTelefono(usuario.getTelefono());
        actual.setActivo(usuario.isActivo());

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            actual.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        if ("admin@flawless.com".equals(actual.getCorreo())) {
            if (rolesIds == null) {
                rolesIds = new java.util.ArrayList<>();
            }
            if (!rolesIds.contains(1L)) {
                rolesIds.add(1L);
            }
        }

        if (rolesIds != null) {
            java.util.List<FlawlessRol> roles = rolRepository.findAllById(rolesIds);
            actual.setRoles(roles);
        } else {
            actual.setRoles(new java.util.ArrayList<>());
        }

        usuarioRepository.save(actual);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {

        FlawlessUsuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null && "admin@flawless.com".equals(usuario.getCorreo())) {
            return "redirect:/admin/usuarios";
        }

        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }

    // PRODUCTOS
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoService.getProductos());
        return "salonpaneladmin/productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new FlawlessProducto());

        model.addAttribute("categorias",
                categoriaService.getCategorias()
                        .stream()
                        .filter(c -> "PRODUCTO".equals(c.getTipo()))
                        .toList()
        );

        return "salonpaneladmin/editarProducto";
    }

    @GetMapping("/editarProducto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {

        FlawlessProducto producto = productoService.getProducto(id);

        if (producto == null) {
            return "redirect:/admin/productos";
        }

        model.addAttribute("producto", producto);

        model.addAttribute("categorias",
                categoriaService.getCategorias()
                        .stream()
                        .filter(c -> "PRODUCTO".equals(c.getTipo()))
                        .toList()
        );

        return "salonpaneladmin/editarProducto";
    }

    @PostMapping("/guardarProducto")
    public String guardarProducto(
            FlawlessProducto producto,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {

        try {

            if (imagenFile != null && !imagenFile.isEmpty()) {

                String original = imagenFile.getOriginalFilename();

                String nombreArchivo = "prod_" + System.currentTimeMillis()
                        + original.substring(original.lastIndexOf("."));

                String ruta = System.getProperty("user.dir")
                        + "/src/main/resources/static/img/productos/";

                java.io.File directorio = new java.io.File(ruta);
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                java.io.File destino = new java.io.File(directorio, nombreArchivo);
                imagenFile.transferTo(destino);

                producto.setImagen("/img/productos/" + nombreArchivo);
            }

            productoService.save(producto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/productos";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable Long id) {

        FlawlessProducto producto = productoService.getProducto(id);

        if (producto != null && producto.getImagen() != null) {

            String ruta = System.getProperty("user.dir")
                    + "/src/main/resources/static"
                    + producto.getImagen();

            java.io.File archivo = new java.io.File(ruta);

            if (archivo.exists()) {
                archivo.delete();
            }
        }

        productoService.delete(id);
        return "redirect:/admin/productos";
    }

    // SERVICIOS
    @GetMapping("/servicios")
    public String servicios(Model model) {
        model.addAttribute("servicios", servicioService.getServicios());
        return "salonpaneladmin/servicios";
    }

    @GetMapping("/servicios/nuevo")
    public String nuevoServicio(Model model) {
        model.addAttribute("servicio", new FlawlessServicio());

        model.addAttribute("categorias",
                categoriaService.getCategorias()
                        .stream()
                        .filter(c -> "SERVICIO".equals(c.getTipo()))
                        .toList()
        );

        return "salonpaneladmin/editarServicio";
    }

    @GetMapping("/editarServicio/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {

        FlawlessServicio servicio = servicioService.getServicio(id);

        if (servicio == null) {
            return "redirect:/admin/servicios";
        }

        model.addAttribute("servicio", servicio);

        model.addAttribute("categorias",
                categoriaService.getCategorias()
                        .stream()
                        .filter(c -> "SERVICIO".equals(c.getTipo()))
                        .toList()
        );

        return "salonpaneladmin/editarServicio";
    }

    @PostMapping("/guardarServicio")
    public String guardarServicio(FlawlessServicio servicio) {
        servicioService.save(servicio);
        return "redirect:/admin/servicios";
    }

    @GetMapping("/eliminarServicio/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        servicioService.delete(id);
        return "redirect:/admin/servicios";
    }

    // CATEGORÍAS
    @GetMapping("/categorias")
    public String categorias(Model model) {
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "salonpaneladmin/categorias";
    }

    @GetMapping("/categorias/nuevo")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new FlawlessCategoria());
        return "salonpaneladmin/editarCategoria";
    }

    @GetMapping("/categorias/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {

        // Buscar la categoría por ID
        FlawlessCategoria categoria = categoriaService.getCategoria(id);

        // Validar si existe
        if (categoria == null) {
            return "redirect:/admin/categorias";
        }

        model.addAttribute("categoria", categoria);

        return "salonpaneladmin/editarCategoria";
    }

    @PostMapping("/categorias/guardar")
    public String guardarCategoria(@ModelAttribute FlawlessCategoria categoria) {
        categoriaService.save(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return "redirect:/admin/categorias";
    }

    // CITAS
    @GetMapping("/citas")
    public String citas(@RequestParam(required = false) String filtro, Model model) {

        java.util.List<FlawlessCita> citas = citaService.getCitas();

        citas = citas.stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .toList();

        model.addAttribute("citas", citas);
        model.addAttribute("filtro", filtro);

        return "salonpaneladmin/citas";
    }

    @GetMapping("/citas/nueva")
    public String nuevaCita(Model model) {
        model.addAttribute("cita", new FlawlessCita());
        return "salonpaneladmin/crearCita";
    }

    // RESERVAS
    @GetMapping("/reservas")
    public String reservas(@RequestParam(required = false) String filtro, Model model) {

        java.util.List<FlawlessReserva> reservas = reservaService.getReservas();

        reservas = reservas.stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .toList();

        model.addAttribute("reservas", reservas);
        model.addAttribute("filtro", filtro);

        return "salonpaneladmin/reservas";
    }

    @GetMapping("/reservas/nueva")
    public String nuevaReserva(Model model) {
        model.addAttribute("reserva", new FlawlessReserva());
        return "salonpaneladmin/crearReserva";
    }

    // PROMOCIONES
    @GetMapping("/promociones")
    public String promociones(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String tipo,
            Model model) {

        var promociones = promocionService.filtrarPromociones(filtro, tipo);

        // calcular precio dinamico (faltaba esto)
        promociones.forEach(p -> {
            Double precio = promocionService.calcularPrecioFinal(p);
            p.setPrecioCalculado(precio);
        });

        model.addAttribute("promociones", promociones);
        model.addAttribute("filtro", filtro);
        model.addAttribute("tipo", tipo);

        return "salonpaneladmin/promociones";
    }

    @GetMapping("/promociones/nueva")
    public String nuevaPromocion(Model model) {

        model.addAttribute("promocion", new FlawlessPromocion());

        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("servicios", servicioService.getServicios());
        model.addAttribute("productos", productoService.getProductos());

        return "salonpaneladmin/editarPromocion";
    }
    
    @PostMapping("/cambiarTipoPromocion")
    public String cambiarTipoPromocion(
            @ModelAttribute FlawlessPromocion promocion,
            Model model) {

        // Mantener lo que el usuario ya seleccionó
        model.addAttribute("promocion", promocion);

        // Recargar listas necesarias para el formulario
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("servicios", servicioService.getServicios());
        model.addAttribute("productos", productoService.getProductos());

        return "salonpaneladmin/editarPromocion";
    }

    @GetMapping("/editarPromocion/{id}")
    public String editarPromocion(@PathVariable Long id, Model model) {

        FlawlessPromocion promocion = promocionService.getPromocionById(id);

        if (promocion == null) {
            return "redirect:/admin/promociones";
        }

        model.addAttribute("promocion", promocion);

        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("servicios", servicioService.getServicios());
        model.addAttribute("productos", productoService.getProductos());

        return "salonpaneladmin/editarPromocion";
    }

    @PostMapping("/guardarPromocion")
    public String guardarPromocion(
            @ModelAttribute FlawlessPromocion promocion,
            @RequestParam(value = "categoria", required = false) Long categoriaId,
            @RequestParam(value = "producto", required = false) Long productoId,
            @RequestParam(value = "servicio", required = false) Long servicioId,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile) {

        try {

            // MAPEAR CATEGORIA
            if (categoriaId != null) {
                FlawlessCategoria categoria = categoriaService.getCategoria(categoriaId);
                promocion.setCategoria(categoria);
            }

            // MAPEAR PRODUCTO
            if (productoId != null) {
                FlawlessProducto producto = productoService.getProducto(productoId);
                promocion.setProducto(producto);
            }

            // MAPEAR SERVICIO
            if (servicioId != null) {
                FlawlessServicio servicio = servicioService.getServicio(servicioId);
                promocion.setServicio(servicio);
            }

            // LIMPIAR RELACIONES SEGÚN TIPO
            if ("SERVICIO".equals(promocion.getTipo())) {
                promocion.setProducto(null);
                promocion.setCantidadMinima(null);
            }

            if ("PRODUCTO".equals(promocion.getTipo())) {
                promocion.setServicio(null);
            }

            // IMAGEN
            if (promocion.getId() != null) {
                FlawlessPromocion existente = promocionService.getPromocionById(promocion.getId());
                if (existente != null && existente.getImagen() != null
                        && (imagenFile == null || imagenFile.isEmpty())) {
                    promocion.setImagen(existente.getImagen());
                }
            }

            if (imagenFile != null && !imagenFile.isEmpty()) {
                String original = imagenFile.getOriginalFilename();
                String nombreArchivo = "promo_" + System.currentTimeMillis()
                        + original.substring(original.lastIndexOf("."));

                String ruta = System.getProperty("user.dir")
                        + "/src/main/resources/static/img/promociones/";

                java.io.File directorio = new java.io.File(ruta);
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                imagenFile.transferTo(new java.io.File(directorio, nombreArchivo));

                promocion.setImagen("/img/promociones/" + nombreArchivo);
            }

            promocionService.save(promocion);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/promociones";
    }

    @GetMapping("/eliminarPromocion/{id}")
    public String eliminarPromocion(@PathVariable Long id) {
        FlawlessPromocion promocion = promocionService.getPromocionById(id);
        if (promocion != null && promocion.getImagen() != null) {
            String ruta = System.getProperty("user.dir")
                    + "/src/main/resources/static" + promocion.getImagen();
            new java.io.File(ruta).delete();
        }
        promocionService.delete(id);
        return "redirect:/admin/promociones";
    }
}