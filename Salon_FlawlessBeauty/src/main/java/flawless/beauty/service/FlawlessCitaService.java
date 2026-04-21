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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlawlessCitaService {

    @Autowired
    private FlawlessCitaRepository citaRepository;

    // Horarios permitidos para la pagina
    private static final List<LocalTime> HORAS_VALIDAS = List.of(
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0)
    );

    // CRUD BÁSICO
    public List<FlawlessCita> getCitas() {
        return citaRepository.findAll();
    }

    public FlawlessCita getCitaById(Long id) {
        return citaRepository.findById(id).orElse(null);
    }

    public FlawlessCita getById(Long id) {
        return getCitaById(id);
    }

    public void save(FlawlessCita cita) {
        citaRepository.save(cita);
    }

    public void delete(FlawlessCita cita) {
        citaRepository.delete(cita);
    }

    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }

    public List<FlawlessCita> findByUsuario(FlawlessUsuario usuario) {
        return citaRepository.findByUsuario(usuario);
    }

    // HORARIOS
    public List<LocalTime> getHorasDisponibles(LocalDate fecha) {

        List<LocalTime> ocupadas = citaRepository.findByFecha(fecha)
                .stream()
                .map(FlawlessCita::getHora)
                .toList();

        return HORAS_VALIDAS.stream()
                .filter(h -> !ocupadas.contains(h))
                .toList();
    }

    // VALIDACIONES
    public boolean existeCitaUsuario(FlawlessUsuario usuario,
                                     LocalDate fecha,
                                     LocalTime hora) {

        return citaRepository.existsByUsuarioAndFechaAndHora(usuario, fecha, hora);
    }

    // editarCita (antes horaOcupada)
    public boolean existeHorarioOcupado(LocalDate fecha, LocalTime hora) {
        return citaRepository.existsByFechaAndHora(fecha, hora);
    }

    public boolean horaOcupada(LocalDate fecha, LocalTime hora) {
        return existeHorarioOcupado(fecha, hora);
    }

    public boolean existeCitaUsuarioFechaHora(FlawlessUsuario usuario,
                                              LocalDate fecha,
                                              LocalTime hora) {
        return existeCitaUsuario(usuario, fecha, hora);
    }
}