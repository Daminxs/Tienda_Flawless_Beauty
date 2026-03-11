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

// Controlar el apartado de perfil del usuario dentro del sistema.
// Verificar si existe una sesión activa antes de mostrar el perfil.
// Obtener la información del usuario almacenada en la sesión.
// Enviar los datos del usuario a la vista para mostrarlos en la página.
// Evitar que usuarios sin sesión accedan al perfil redirigiéndolos al login.

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessPerfilController {

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {

        if (session.getAttribute("correo") == null) {
            return "redirect:/login";
        }

        String nombre = (String) session.getAttribute("nombre");
        String correo = (String) session.getAttribute("correo");

        model.addAttribute("nombre", nombre);
        model.addAttribute("correo", correo);

        return "perfil";
    }
}