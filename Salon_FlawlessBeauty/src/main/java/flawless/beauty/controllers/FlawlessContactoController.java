/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * Encargado para: Daniela Navarro
 */

// Esta clase debe hacer lo siguiente o contener:
// Tomando en cuenta lo hablado en el avance 1, de sus requerimientos y funciones.
// Controlar el apartado de Contacto del sitio web.
// Recibir las solicitudes del navegador cuando el usuario entra a la sección "Contacto".
// Mostrar la página contacto.html ubicada en la carpeta templates.
// Permitir mostrar información del salón como teléfono, correo o ubicación.

@Controller
public class FlawlessContactoController {
    
    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto";
    }
}