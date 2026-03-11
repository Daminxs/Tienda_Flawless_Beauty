/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.controllers;

/**
 *
 * Encargado para: Damian Perez
 */

//

//

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
