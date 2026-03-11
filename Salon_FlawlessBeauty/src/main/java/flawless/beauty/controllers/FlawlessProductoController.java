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

import flawless.beauty.domain.FlawlessProducto;
import flawless.beauty.service.FlawlessProductoService;
import flawless.beauty.service.FlawlessCategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class FlawlessProductoController {

    private final FlawlessProductoService productoService;
    private final FlawlessCategoriaService categoriaService;

    public FlawlessProductoController(
            FlawlessProductoService productoService,
            FlawlessCategoriaService categoriaService) {

        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/salonproductos")
    public String listado(@RequestParam(required = false) Long categoria, Model model) {

        var categorias = categoriaService.getCategoriasProductos();

        List<FlawlessProducto> productos;

        if (categoria == null) {
            productos = productoService.getProductos();
        } else {
            productos = productoService.getProductosPorCategoria(categoria);
        }

        model.addAttribute("categorias", categorias);
        model.addAttribute("productos", productos);

        return "salonproductos/listado";
    }
}