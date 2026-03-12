/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

import flawless.beauty.domain.FlawlessPromocion;
import flawless.beauty.service.FlawlessPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * Encargado para: Monica Garcia
 */

// Esta clase debe hacer lo siguiente:

// Mostrar la página donde se visualizan las promociones del salón.
// Mostrar promociones mediante imágenes con su precio y descripción.
// Las promociones se deben ver bien con separacion para que el usuario pueda verlas fácilmente.

@Controller
@RequestMapping("/salonpromociones")
public class FlawlessPromocionController {

    @Autowired
    private FlawlessPromocionService promocionService;

    @GetMapping("")
    public String listado(Model model) {
        var promociones = promocionService.getPromociones();
        model.addAttribute("promociones", promociones);
        return "/salonpromociones/listado";
    }

    // Es la que recibe los datos del formulario de 'modifica'
    @PostMapping("/guardar")
    public String guardar(FlawlessPromocion promocion) {
        promocionService.save(promocion);
        return "redirect:/salonpromociones"; // Después de guardar, te devuelve al listado
    }
}
