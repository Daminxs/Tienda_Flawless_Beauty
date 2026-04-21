package flawless.beauty.controllers;

import flawless.beauty.service.FlawlessPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Encargado para: Monica Garcia
 */
@Controller
@RequestMapping("/salonpromociones")
public class FlawlessPromocionController {

    @Autowired
    private FlawlessPromocionService promocionService;

    @GetMapping("")
    public String listado(Model model) {
        model.addAttribute("promociones", promocionService.getPromocionesActivas());
        return "/salonpromociones/listado";
    }
}
