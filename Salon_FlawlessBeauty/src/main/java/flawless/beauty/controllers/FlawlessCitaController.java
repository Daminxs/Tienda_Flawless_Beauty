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

// Esta clase controla todas las funcionalidades relacionadas
// con las citas dentro del sistema Flawless Beauty.

// Mostrar el formulario para agendar una cita.
// Guardar nuevas citas en la base de datos.
// Mostrar al usuario sus citas registradas.
// Permitir visualizar las reservas existentes.

import flawless.beauty.domain.*;
import flawless.beauty.repository.*;
import flawless.beauty.service.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FlawlessCitaController {

    private final FlawlessUsuarioRepository usuarioRepository;
    private final FlawlessServicioRepository servicioRepository;
    private final FlawlessCitaService citaService;

    public FlawlessCitaController(
            FlawlessUsuarioRepository usuarioRepository,
            FlawlessServicioRepository servicioRepository,
            FlawlessCitaService citaService) {

        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
        this.citaService = citaService;
    }

    @GetMapping("/agendarCita")
    public String agendarCita(@RequestParam Long id,
                              Authentication auth,
                              Model model) {

        String correo = auth.getName();

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "redirect:/login";
        }

        FlawlessServicio servicio = servicioRepository.findById(id)
                .orElse(null);

        if (servicio == null) {
            model.addAttribute("error", "Servicio no encontrado");
            return "perfil";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("servicio", servicio);

        return "agendarCita";
    }

    @PostMapping("/guardarCita")
    public String guardarCita(
            @RequestParam Long servicioId,
            FlawlessCita cita,
            Authentication auth,
            Model model) {

        String correo = auth.getName();

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "redirect:/login";
        }

        FlawlessServicio servicio = servicioRepository.findById(servicioId)
                .orElse(null);

        if (servicio == null) {
            model.addAttribute("mensaje", "Servicio no encontrado");
            return "agendarCita";
        }

        cita.setUsuario(usuario);
        cita.setServicio(servicio);

        long numero = citaService.getCitas().size() + 1;
        cita.setCodigo(String.format("CITA-%05d", numero));

        citaService.save(cita);

        return "redirect:/verCitas";
    }

    @GetMapping("/misCitas")
    public String misCitas(Authentication auth, Model model) {

        String correo = auth.getName();

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("citas", citaService.findByUsuario(usuario));

        return "misCitas";
    }
}