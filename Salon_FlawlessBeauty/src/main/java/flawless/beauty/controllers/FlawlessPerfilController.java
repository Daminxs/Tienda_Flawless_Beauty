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

import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessPerfilController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @GetMapping("/perfil")
    public String perfil(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        String correo = principal.getName();

        FlawlessUsuario usuario =
                usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("nombre", usuario.getNombre());
        model.addAttribute("correo", usuario.getCorreo());
        model.addAttribute("telefono", usuario.getTelefono());

        return "perfil";
    }
}