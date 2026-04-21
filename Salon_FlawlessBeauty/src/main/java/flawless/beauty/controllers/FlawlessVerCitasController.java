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

//

import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.service.FlawlessCitaService;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessVerCitasController {

    @Autowired
    private FlawlessCitaService citaService;

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @GetMapping("/verCitas")
    public String verCitas(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        String correo = principal.getName();

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("citas", citaService.findByUsuario(usuario));

        return "verCitas";
    }
}