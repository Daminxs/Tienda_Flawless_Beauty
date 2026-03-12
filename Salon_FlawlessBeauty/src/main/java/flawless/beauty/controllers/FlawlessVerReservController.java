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
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.service.FlawlessReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessVerReservController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessReservaService reservaService;

    @GetMapping("/verReservas")
    public String verReservas(HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");

        if (correo == null) {
            return "redirect:/login";
        }

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("reservas", reservaService.findByUsuario(usuario));

        return "verReservas";
    }
}