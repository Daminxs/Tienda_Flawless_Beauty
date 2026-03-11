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

import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.domain.FlawlessCita;
import flawless.beauty.domain.FlawlessServicio;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.repository.FlawlessServicioRepository;
import flawless.beauty.service.FlawlessCitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FlawlessCitaController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessServicioRepository servicioRepository;

    @Autowired
    private FlawlessCitaService citaService;

    @GetMapping("/agendarCita")
    public String agendarCita(@RequestParam Long id, HttpSession session, Model model) {

        if (session.getAttribute("correo") == null) {
            model.addAttribute("mensaje", "Debes iniciar sesión para agendar una cita");
            return "login";
        }

        String correo = (String) session.getAttribute("correo");
        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        FlawlessServicio servicio = servicioRepository.findById(id).orElse(null);

        model.addAttribute("usuario", usuario);
        model.addAttribute("servicio", servicio);

        return "agendarCita";
    }

    @PostMapping("/guardarCita")
    public String guardarCita(
            @RequestParam Long servicioId,
            FlawlessCita cita,
            HttpSession session) {

        if (session.getAttribute("correo") == null) {
            return "redirect:/login";
        }

        String nombre = (String) session.getAttribute("nombre");
        cita.setNombreCliente(nombre);

        FlawlessServicio servicio = servicioRepository.findById(servicioId).orElse(null);
        cita.setServicio(servicio);

        citaService.save(cita);

        return "redirect:/perfil";
    }

}
