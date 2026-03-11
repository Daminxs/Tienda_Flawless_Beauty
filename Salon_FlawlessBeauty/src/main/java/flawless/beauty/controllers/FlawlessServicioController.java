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

// Controlar el apartado de Servicios dentro del sitio web.
// Recibir las solicitudes cuando el usuario entra a la sección de servicios.
// Permite mostrar como se listan los servicios que ofrece el salón.
// Mostrar información de cada servicio como nombre, descripción y precio.
// Permitir que el usuario pueda seleccionar un servicio si desea agendar una cita.

import flawless.beauty.domain.FlawlessServicio;
import flawless.beauty.service.FlawlessServicioService;
import flawless.beauty.service.FlawlessCategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class FlawlessServicioController {

    private final FlawlessServicioService servicioService;
    private final FlawlessCategoriaService categoriaService;

    public FlawlessServicioController(FlawlessServicioService servicioService,
                                      FlawlessCategoriaService categoriaService) {
        this.servicioService = servicioService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/salonservicios")
    public String listado(@RequestParam(required = false) Long categoria, Model model) {

        var categorias = categoriaService.getCategoriasServicios();

        List<FlawlessServicio> servicios;

        if (categoria == null) {
            servicios = servicioService.getServicios();
        } else {
            servicios = servicioService.getServiciosPorCategoria(categoria);
        }

        model.addAttribute("categorias", categorias);
        model.addAttribute("servicios", servicios);

        return "salonservicios/listado";
    }
    
}