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

// Controlar el apartado de reserva de productos dentro del sitio web.
// Recibir la solicitud cuando un usuario desea reservar un producto.
// Mostrar una ventana donde el usuario puede seleccionar un producto para reservar.
// Guardar en la base de datos la información de la reserva realizada por el usuario.
// Registrar datos como el usuario, el producto seleccionado y la fecha de la reserva.

import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.domain.FlawlessProducto;
import flawless.beauty.domain.FlawlessReserva;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import flawless.beauty.repository.FlawlessProductoRepository;
import flawless.beauty.service.FlawlessReservaService;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FlawlessReservaController {

    @Autowired
    private FlawlessUsuarioRepository usuarioRepository;

    @Autowired
    private FlawlessProductoRepository productoRepository;

    @Autowired
    private FlawlessReservaService reservaService;

    @GetMapping("/reservarProducto")
    public String reservarProducto(@RequestParam Long id,
                                   Authentication auth,
                                   Model model) {

        if (auth == null) return "redirect:/login";

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(auth.getName());
        FlawlessProducto producto = productoRepository.findById(id).orElse(null);

        if (producto == null || usuario == null) {
            return "redirect:/salonproductos";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("producto", producto);

        return "reservar";
    }

    @PostMapping("/guardarReserva")
    public String guardarReserva(
            @RequestParam Long productoId,
            FlawlessReserva reserva,
            Authentication auth,
            Model model) {

        FlawlessUsuario usuario = usuarioRepository.findByCorreo(auth.getName());
        FlawlessProducto producto = productoRepository.findById(productoId).orElse(null);

        if (producto == null || usuario == null) {
            return "redirect:/salonproductos";
        }

        // STOCK
        if (producto.getStock() <= 0 || producto.getStock() < reserva.getCantidad()) {
            model.addAttribute("mensaje", "No hay suficiente stock");
            return "reservar";
        }

        producto.setStock(producto.getStock() - reserva.getCantidad());
        productoRepository.save(producto);

        reserva.setUsuario(usuario);
        reserva.setProducto(producto);

        reserva.setCodigo("RES-" + System.currentTimeMillis());

        reservaService.save(reserva);

        return "redirect:/verReservas";
    }
    
}