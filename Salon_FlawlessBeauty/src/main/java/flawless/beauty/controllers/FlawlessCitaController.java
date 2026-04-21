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

import java.time.LocalDate;
import java.time.LocalTime;

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

        if (auth == null) return "redirect:/login";

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(auth.getName());
        FlawlessServicio servicio = servicioRepository.findById(id).orElse(null);

        if (usuario == null || servicio == null) {
            return "redirect:/salonservicios";
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

        if (auth == null) return "redirect:/login";

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(auth.getName());
        FlawlessServicio servicio = servicioRepository.findById(servicioId).orElse(null);

        if (usuario == null || servicio == null) {
            return "redirect:/salonservicios";
        }

        // Validacion en caso de doble cita mismo usuario
        boolean yaTiene = citaService.existeCitaUsuarioFechaHora(
                usuario, cita.getFecha(), cita.getHora()
        );

        if (yaTiene) {
            model.addAttribute("error", "Ya tienes una cita en esa fecha y hora");
            return "agendarCita";
        }

        // Validacion en caso de hora ocupada
        boolean ocupada = citaService.horaOcupada(cita.getFecha(), cita.getHora());

        if (ocupada) {
            model.addAttribute("error", "Esa hora ya está ocupada");
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

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(auth.getName());

        model.addAttribute("citas", citaService.findByUsuario(usuario));

        return "misCitas";
    }
}