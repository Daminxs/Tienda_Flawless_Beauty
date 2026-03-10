/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

/**
 *
 * Encargado para: Damian Perez
 */

// Esta clase debe hacer lo siguiente:

// Controlar el apartado de Categorías dentro del sitio web.
// Recibir las solicitudes cuando se necesita consultar las categorías de servicios o productos.
// Mostrar las categorías disponibles para organizar los servicios y productos.
// Permitir utilizar las categorías para clasificar los servicios y productos dentro del sistema.

import flawless.beauty.domain.FlawlessCategoria;
import flawless.beauty.service.FlawlessCategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categoria")
public class FlawlessCategoriaController {

    private final FlawlessCategoriaService categoriaService;

    public FlawlessCategoriaController(FlawlessCategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        return "/saloncategorias/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new FlawlessCategoria());
        return "/saloncategorias/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(FlawlessCategoria categoria) {
        categoriaService.save(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, Model model) {
        var categoria = categoriaService.getCategoria(id);
        model.addAttribute("categoria", categoria);
        return "/saloncategorias/modifica";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        categoriaService.delete(id);
        return "redirect:/categoria/listado";
    }
}