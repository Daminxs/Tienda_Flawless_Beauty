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
import flawless.beauty.service.FlawlessCorreoService;
import flawless.beauty.repository.FlawlessRolRepository;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.domain.FlawlessResetPassword;
import flawless.beauty.repository.FlawlessResetPasswordRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class FlawlessUsuarioController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private FlawlessResetPasswordRepository resetPasswordRepository;
    
    @Autowired
    private FlawlessCorreoService correoService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }
    
    @GetMapping("/recuperar")
    public String recuperar() {
        return "recuperar";
    }
    
    @GetMapping("/restablecer")
    public String mostrarRestablecer(String token, Model model) {

        FlawlessResetPassword reset
                = resetPasswordRepository.findByToken(token);

        if (reset == null) {
            return "redirect:/login?error";
        }

        if (reset.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return "redirect:/login?expired";
        }

        model.addAttribute("token", token);

        return "restablecer";
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
    
    @PostMapping("/recuperar")
    public String processForgotPassword(String correo, Model model) {

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            model.addAttribute("mensaje", "Si el correo existe, se enviaron instrucciones");
            return "recuperar";
        }

        String token = UUID.randomUUID().toString();

        FlawlessResetPassword existente
                = resetPasswordRepository.findByUsuario(usuario);

        if (existente != null) {
            resetPasswordRepository.delete(existente);
        }

        FlawlessResetPassword reset = new FlawlessResetPassword();
        reset.setToken(token);
        reset.setUsuario(usuario);
        reset.setFechaExpiracion(LocalDateTime.now().plusHours(1));

        resetPasswordRepository.save(reset);

        String link = "http://localhost/restablecer?token=" + token;

        correoService.enviarCorreo(
                usuario.getCorreo(),
                "Recuperación de contraseña - Flawless Beauty",
                "Hola " + usuario.getNombre() + ",\n\n"
                + "Haz clic en el siguiente enlace para restablecer tu contraseña:\n\n"
                + link
                + "\n\nEste enlace expirará en 1 hora.\n\n"
                + "Si no solicitaste este cambio, ignora este mensaje."
        );

        model.addAttribute("mensaje", "Se ha enviado un correo con instrucciones");

        return "recuperar";
    }
    
    @PostMapping("/restablecer")
    public String procesarRestablecer(String token, String password) {

        FlawlessResetPassword reset
                = resetPasswordRepository.findByToken(token);

        if (reset == null) {
            return "redirect:/login?error";
        }

        if (reset.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return "redirect:/login?expired";
        }

        FlawlessUsuario usuario = reset.getUsuario();

        usuario.setPassword(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);

        resetPasswordRepository.delete(reset);

        return "redirect:/login?resetSuccess";
    }
}
