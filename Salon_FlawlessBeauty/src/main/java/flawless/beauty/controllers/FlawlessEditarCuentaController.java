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

// Controlar la página de edición de datos del usuario.
// Mostrar el formulario donde el usuario puede modificar su información personal.
// Permitir en el futuro actualizar datos como nombre, correo o contraseña.
// Servir como interfaz para la actualización de la información de la cuenta.

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlawlessEditarCuentaController {

    @GetMapping("/editarCuenta")
    public String editarCuenta(){
        return "editarCuenta";
    }

}
