package flawless.beauty.controllers;

import flawless.beauty.service.FlawlessPromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Encargado para: Monica Garcia
 */

@Controller
@RequestMapping("/salonpromociones")
public class FlawlessPromocionController {

    @Autowired
    private FlawlessPromocionService promocionService;

    @GetMapping("")
    public String listado(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String tipo,
            Model model) {

        var promociones = promocionService.filtrarPromociones(filtro, tipo);

        // FILTRAR SOLO ACTIVAS
        promociones = promociones.stream()
                .filter(p -> p.isActivo())
                .toList();

        // calcular precio dinamico
        promociones.forEach(p -> {
            Double precio = promocionService.calcularPrecioFinal(p);
            p.setPrecioCalculado(precio);
        });

        model.addAttribute("promociones", promociones);
        model.addAttribute("filtro", filtro);
        model.addAttribute("tipo", tipo);

        return "/salonpromociones/listado";
    }
}