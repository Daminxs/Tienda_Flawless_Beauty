/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

/**
 *
 * Encargado para: Damian Perez
 */

//

import flawless.beauty.domain.FlawlessUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessPerfilController {

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {

        FlawlessUsuario usuario = (FlawlessUsuario) session.getAttribute("usuario");

        // Si no hay usuario en sesión, redirigir al login
        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("correo", usuario.getCorreo());

        return "perfil";
    }
}