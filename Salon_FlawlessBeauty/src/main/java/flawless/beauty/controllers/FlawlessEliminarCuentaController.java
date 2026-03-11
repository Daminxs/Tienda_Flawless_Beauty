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

// Controlar la eliminación de la cuenta del usuario.
// Recibir la solicitud cuando el usuario decide eliminar su cuenta.
// Ejecutar el proceso de eliminación (actualmente simulado).
// En el futuro eliminar los datos del usuario de la base de datos.
// Redirigir al usuario a la página principal después de eliminar su cuenta.

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlawlessEliminarCuentaController {

    @PostMapping("/eliminarCuenta")
    public String eliminarCuenta(){


        System.out.println("Cuenta eliminada (simulación)");

        return "redirect:/";
    }

}
