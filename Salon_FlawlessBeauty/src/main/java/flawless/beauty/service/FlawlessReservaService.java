/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.service;

/**
 *
 * Encargado para: Damian Perez
 */

// Esta clase debe hacer lo siguiente:

// Gestionar la lógica del negocio relacionada con las reservas de productos.
// Permitir registrar las reservas de productos realizadas por los usuarios.
// Utilizar el repository de ReservaProducto para guardar la información en la base de datos.
// Servir como intermediario entre el controller y el repository para gestionar las reservas de productos.

import flawless.beauty.domain.FlawlessReserva;
import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessReservaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlawlessReservaService {

    @Autowired
    private FlawlessReservaRepository reservaRepository;

    public List<FlawlessReserva> getReservas() {
        return reservaRepository.findAll();
    }

    public FlawlessReserva getReserva(FlawlessReserva reserva) {
        return reservaRepository.findById(reserva.getId()).orElse(null);
    }

    public void save(FlawlessReserva reserva) {
        reservaRepository.save(reserva);
    }

    public void delete(FlawlessReserva reserva) {
        reservaRepository.delete(reserva);
    }

    public List<FlawlessReserva> findByUsuario(FlawlessUsuario usuario) {
        return reservaRepository.findByUsuario(usuario);
    }

}