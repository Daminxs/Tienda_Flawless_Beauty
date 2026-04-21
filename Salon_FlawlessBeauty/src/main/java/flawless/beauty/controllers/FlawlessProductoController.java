/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

/**
 *
 * Encargado para: Jose Ulate
 */

// Esta clase debe hacer lo siguiente:

// Controlar el apartado de Productos dentro del sitio web.
// Recibir las solicitudes del navegador cuando el usuario entra a la sección de productos.
// Mostrar la página donde se listan los productos disponibles del salón.
// Permitir mostrar información de cada producto como nombre, descripción, precio e imagen.
// Permitir que el usuario vea los productos y pueda seleccionar uno para realizar una reserva.

import flawless.beauty.domain.*;
import flawless.beauty.repository.*;
import flawless.beauty.service.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salonproductos")
public class FlawlessProductoController {

    private final FlawlessProductoService productoService;
    private final FlawlessCategoriaService categoriaService;
    private final FlawlessProductoRepository productoRepository;
    private final FlawlessUsuarioRepository usuarioRepository;
    private final FlawlessReservaService reservaService;

    public FlawlessProductoController(
            FlawlessProductoService productoService,
            FlawlessCategoriaService categoriaService,
            FlawlessProductoRepository productoRepository,
            FlawlessUsuarioRepository usuarioRepository,
            FlawlessReservaService reservaService) {

        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.reservaService = reservaService;
    }

    @GetMapping
    public String listado(@RequestParam(required = false) Long categoria, Model model) {

        List<FlawlessCategoria> categorias =
                categoriaService.getCategoriasProductos();

        List<FlawlessProducto> productos =
                (categoria == null)
                        ? productoService.getProductos()
                        : productoService.getProductosPorCategoria(categoria);

        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);

        return "salonproductos/listado";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(
            @RequestParam Long productoId,
            FlawlessReserva reserva,
            Authentication auth,
            Model model) {

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(auth.getName());
        FlawlessProducto producto = productoRepository.findById(productoId).orElse(null);

        if (usuario == null || producto == null) {
            return "redirect:/salonproductos";
        }

        if (producto.getStock() < reserva.getCantidad()) {
            model.addAttribute("mensaje", "Stock insuficiente");
            return "salonproductos/listado";
        }

        producto.setStock(producto.getStock() - reserva.getCantidad());
        productoRepository.save(producto);

        reserva.setUsuario(usuario);
        reserva.setProducto(producto);
        reserva.setCodigo("RES-" + System.currentTimeMillis());

        reservaService.save(reserva);

        return "redirect:/verReservas";
    }
}