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

// Controlar la página principal del sitio web.
// Recibir la solicitud cuando el usuario entra a la página principal del sistema.
// Mostrar la página index.html ubicada en la carpeta templates.
// Este apartado es para que el usuario pueda navegar hacia las demás secciones del sitio.

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String inicio() {
        return "index";
    }
}