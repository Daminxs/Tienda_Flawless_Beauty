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

import flawless.beauty.domain.FlawlessCita;
import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.service.FlawlessCitaService;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.security.core.Authentication;

@Controller
public class FlawlessVerCitasController {

    @Autowired
    private FlawlessCitaService citaService;

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @GetMapping("/verCitas")
    public String verCitas(Authentication auth, Model model) {

        if (auth == null) return "redirect:/login";

        FlawlessUsuario usuario =
                usuarioRepository.findByCorreo(auth.getName());

        model.addAttribute("citas",
                citaService.findByUsuario(usuario)
                        .stream()
                        .sorted((a, b) -> b.getId().compareTo(a.getId()))
                        .toList()
        );

        return "verCitas";
    }

    @PostMapping("/editarCita")
    public String editarCita(
            @RequestParam Long id,
            @RequestParam String fecha,
            @RequestParam String hora,
            Authentication auth,
            Model model) {

        FlawlessCita cita = citaService.getCitaById(id);

        if (cita == null) return "redirect:/verCitas";

        if (!cita.getUsuario().getCorreo().equals(auth.getName())) {
            return "redirect:/verCitas";
        }

        LocalDate nuevaFecha = LocalDate.parse(fecha);
        LocalTime nuevaHora = LocalTime.parse(hora);

        // evitar duplicados al editar
        boolean ocupada = citaService.horaOcupada(nuevaFecha, nuevaHora);

        if (ocupada) {
            return "redirect:/verCitas?error=hora_ocupada";
        }

        cita.setFecha(nuevaFecha);
        cita.setHora(nuevaHora);

        citaService.save(cita);

        return "redirect:/verCitas";
    }

    @GetMapping("/cancelarCita/{id}")
    public String cancelarCita(@PathVariable Long id,
                               Authentication auth) {

        FlawlessCita cita = citaService.getCitaById(id);

        if (cita != null &&
            cita.getUsuario().getCorreo().equals(auth.getName())) {

            citaService.delete(cita);
        }

        return "redirect:/verCitas";
    }
}