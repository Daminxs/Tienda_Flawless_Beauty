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

import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlawlessUsuarioController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(String correo,
            String password,
            HttpSession session,
            Model model) {

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario != null && usuario.getPassword().equals(password)) {

            session.setAttribute("nombre", usuario.getNombre());
            session.setAttribute("correo", usuario.getCorreo());
            session.setAttribute("telefono", usuario.getTelefono());

            return "redirect:/perfil";
        }

        model.addAttribute("error", "Correo o contraseña incorrectos");
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
        usuario.setPassword(password);
        usuario.setActivo(true);

        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

    @GetMapping("/editarCuenta")
    public String editarCuenta(HttpSession session, Model model) {

        String correo = (String) session.getAttribute("correo");

        if (correo == null) {
            return "redirect:/login";
        }

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        model.addAttribute("usuario", usuario);

        return "editarCuenta";
    }

    @PostMapping("/editarCuenta")
    public String actualizarCuenta(FlawlessUsuario usuario,
            HttpSession session) {

        usuarioRepository.save(usuario);

        session.setAttribute("nombre", usuario.getNombre());
        session.setAttribute("correo", usuario.getCorreo());
        session.setAttribute("telefono", usuario.getTelefono());

        return "redirect:/perfil";
    }

    @PostMapping("/eliminarCuenta")
    public String eliminarCuenta(HttpSession session) {

        String correo = (String) session.getAttribute("correo");

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}
