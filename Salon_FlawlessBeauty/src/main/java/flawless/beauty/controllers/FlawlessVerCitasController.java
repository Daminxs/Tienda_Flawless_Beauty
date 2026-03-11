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
import jakarta.servlet.http.HttpSession;
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
    public String verCitas(HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");
        if (correo == null) {
            model.addAttribute("error", "Debes iniciar sesión para ver tus citas");
            return "login"; // Retorna el login para que el mensaje aparezca
        }

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "login";
        }

        model.addAttribute("citas", citaService.findByUsuario(usuario));
        return "verCitas";
    }
}