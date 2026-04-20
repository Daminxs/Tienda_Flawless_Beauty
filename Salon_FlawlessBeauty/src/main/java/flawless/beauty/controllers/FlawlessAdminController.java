/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

/**
 *
 * @author Damin
 */

import flawless.beauty.domain.*;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.repository.FlawlessRolRepository;
import flawless.beauty.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        FlawlessUsuario usuario = usuarioRepository.findById(id).orElse(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        return "salonpaneladmin/editarUsuario";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(
            FlawlessUsuario usuario,
            @RequestParam(value = "roles", required = false) java.util.List<Long> rolesIds) {

        FlawlessUsuario actual = usuarioRepository
                .findById(usuario.getIdUsuario())
                .orElse(null);

        if (actual != null) {

            actual.setNombre(usuario.getNombre());
            actual.setCorreo(usuario.getCorreo());
            actual.setTelefono(usuario.getTelefono());
            actual.setActivo(usuario.isActivo());

            // PROTEGER ADMIN
            if (actual.getCorreo().equals("admin@flawless.com")) {

                if (rolesIds == null) {
                    rolesIds = new java.util.ArrayList<>();
                }

                boolean tieneAdmin = rolesIds.contains(1L);

                if (!tieneAdmin) {
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
        }

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {

        FlawlessUsuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null && usuario.getCorreo().equals("admin@flawless.com")) {
            return "redirect:/admin/usuarios";
        }

        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }

    //  PRODUCTOS 
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoService.getProductos());
        return "salonpaneladmin/productos";
    }

    // NUEVO PRODUCTO
    @GetMapping("/productos/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new FlawlessProducto());
        model.addAttribute("categorias", categoriaService.getCategoriasProductos());
        return "salonpaneladmin/editarProducto";
    }

    // EDITAR PRODUCTO
    @GetMapping("/editarProducto/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.getProducto(id));
        model.addAttribute("categorias", categoriaService.getCategoriasProductos());
        return "salonpaneladmin/editarProducto";
    }

    @PostMapping("/guardarProducto")
    public String guardarProducto(FlawlessProducto producto) {
        productoService.save(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.delete(id);
        return "redirect:/admin/productos";
    }

    // SERVICIOS 

    @GetMapping("/servicios")
    public String servicios(Model model) {
        model.addAttribute("servicios", servicioService.getServicios());
        return "salonpaneladmin/servicios";
    }

    // NUEVO SERVICIO
    @GetMapping("/servicios/nuevo")
    public String nuevoServicio(Model model) {
        model.addAttribute("servicio", new FlawlessServicio());
        model.addAttribute("categorias", categoriaService.getCategoriasServicios());
        return "salonpaneladmin/editarServicio";
    }

    // EDITAR SERVICIO
    @GetMapping("/editarServicio/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        model.addAttribute("servicio", servicioService.getServicio(id));
        model.addAttribute("categorias", categoriaService.getCategoriasServicios());
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

    // CATEGORIAS

    @GetMapping("/categorias")
    public String categorias(Model model) {
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "salonpaneladmin/categorias";
    }

    // NUEVA CATEGORIA
    @GetMapping("/categorias/nuevo")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new FlawlessCategoria());
        return "salonpaneladmin/editarCategoria";
    }

    // EDITAR CATEGORIA
    @GetMapping("/editarCategoria/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.getCategoria(id).orElse(null));
        return "salonpaneladmin/editarCategoria";
    }

    @PostMapping("/guardarCategoria")
    public String guardarCategoria(FlawlessCategoria categoria) {
        categoriaService.save(categoria);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/eliminarCategoria/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.delete(id);
        return "redirect:/admin/categorias";
    }

    // CITAS
    
    @GetMapping("/citas")
    public String citas(Model model) {
        model.addAttribute("citas", citaService.getCitas());
        return "salonpaneladmin/citas";
    }

    // RESERVAS

    @GetMapping("/reservas")
    public String reservas(Model model) {
        model.addAttribute("reservas", reservaService.getReservas());
        return "salonpaneladmin/reservas";
    }
}