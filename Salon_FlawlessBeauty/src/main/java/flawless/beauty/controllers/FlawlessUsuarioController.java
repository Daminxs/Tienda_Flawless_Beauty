/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

/**
 *
 * Encargado para: Daniela Navarro
 */

// Esta clase debe hacer lo siguiente:

// Controlar el apartado de usuarios dentro del sitio web.
// Manejar el inicio de sesión (login) de los usuarios.
// Manejar el registro de nuevos usuarios (registro) en el sistema.
// Recibir los datos que el usuario ingresa en los formularios de (login y registro html)
// Guardar la información del nuevo usuario en la base de datos.
// Permitir que el usuario pueda acceder a su cuenta dentro del sistema.

import flawless.beauty.domain.FlawlessRol;
import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessRolRepository;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlawlessUsuarioController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String guardarUsuario(String nombre,
            String correo,
            String telefono,
            String password,
            Model model) {

        FlawlessUsuario existe = usuarioRepository.findByCorreo(correo);

        if (existe != null) {
            model.addAttribute("error", "El correo ya está registrado");
            return "registro";
        }

        FlawlessUsuario usuario = new FlawlessUsuario();

        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setActivo(true);

        FlawlessRol rol = rolRepository.findById(2L).orElse(null);
        usuario.setRoles(List.of(rol));

        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

    @GetMapping("/editarCuenta")
    public String editarCuenta(Principal principal, Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        String correo = principal.getName();

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        model.addAttribute("usuario", usuario);

        return "editarCuenta";
    }

    @PostMapping("/editarCuenta")
    public String actualizarCuenta(FlawlessUsuario usuario) {

        FlawlessUsuario actual =
                usuarioRepository.findById(usuario.getIdUsuario())
                        .orElse(null);

        if (actual != null) {

            actual.setNombre(usuario.getNombre());
            actual.setCorreo(usuario.getCorreo());
            actual.setTelefono(usuario.getTelefono());

            usuarioRepository.save(actual);
        }

        return "redirect:/perfil";
    }

    @PostMapping("/eliminarCuenta")
    public String eliminarCuenta(Principal principal) {

        if (principal != null) {

            String correo = principal.getName();

            FlawlessUsuario usuario
                    = usuarioRepository.findByCorreo(correo);

            if (usuario != null) {
                usuarioRepository.delete(usuario);
            }
        }

        return "redirect:/login?logout=true";
    }
}