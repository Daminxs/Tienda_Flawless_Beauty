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

// Gestionar la lógica relacionada con las citas del sistema.
// Permite obtener la lista de citas registradas.
// Permite guardar nuevas citas cuando un cliente agenda un servicio.
// Permite eliminar citas existentes.
// Utiliza el repository de citas para interactuar con la base de datos.

import flawless.beauty.domain.FlawlessCita;
import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessCitaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlawlessCitaService {

    @Autowired
    private FlawlessCitaRepository citaRepository;

    public List<FlawlessCita> getCitas() {
        return citaRepository.findAll();
    }

    public FlawlessCita getCita(FlawlessCita cita) {
        return citaRepository.findById(cita.getId()).orElse(null);
    }

    public void save(FlawlessCita cita) {
        citaRepository.save(cita);
    }

    public void delete(FlawlessCita cita) {
        citaRepository.delete(cita);
    }

    public List<FlawlessCita> findByUsuario(FlawlessUsuario usuario) {
        return citaRepository.findByUsuario(usuario);
    }
}