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

// Controlar el apartado de cuenta dentro del sitio web.
// Mostrar la página donde el usuario puede elegir entre iniciar sesión o registrarse.
// Dirigir al usuario a las opciones disponibles para acceder o crear una cuenta.
// Servir como punto de entrada al sistema de autenticación del sitio.

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessCuentaController {

    @GetMapping("/cuenta")
    public String cuenta() {
        return "cuenta";
    }

}