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

import flawless.beauty.domain.FlawlessReserva;
import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.service.FlawlessReservaService;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FlawlessVerReservController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessReservaService reservaService;

    @GetMapping("/verReservas")
    public String verReservas(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        FlawlessUsuario usuario =
                usuarioRepository.findByCorreo(principal.getName());

        model.addAttribute("reservas",
                reservaService.findByUsuario(usuario)
                        .stream()
                        .sorted((a, b) -> b.getId().compareTo(a.getId()))
                        .toList()
        );

        return "verReservas";
    }

    @GetMapping("/cancelarReserva/{id}")
    public String cancelarReserva(@PathVariable Long id,
                                  Principal principal) {

        FlawlessReserva reserva = reservaService.getReservaById(id);

        if (reserva != null &&
            reserva.getUsuario().getCorreo().equals(principal.getName())) {

            reservaService.delete(reserva);
        }

        return "redirect:/verReservas";
    }
    
}