/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.service;

/**
 *
 * Encargado para: Jose Ulate
 */

// Esta clase debe hacer lo siguiente:

// Gestionar la lógica del negocio relacionada con los servicios del salón.
// Permitir consultar y manejar la información de los servicios disponibles.
// Utilizar el repository de Servicio para acceder a la base de datos.
// Servir como intermediario entre el controller y el repository cuando se trabaja con servicios.

import flawless.beauty.domain.FlawlessServicio;
import flawless.beauty.repository.FlawlessServicioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FlawlessServicioService {

    private final FlawlessServicioRepository servicioRepository;

    public FlawlessServicioService(FlawlessServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<FlawlessServicio> getServicios() {
        return servicioRepository.findAll();
    }

    public FlawlessServicio getServicio(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    public void save(FlawlessServicio servicio) {
        servicioRepository.save(servicio);
    }

    public void delete(Long id) {
        servicioRepository.deleteById(id);
    }
    
    public List<FlawlessServicio> getServiciosPorCategoria(Long categoriaId) {
        return servicioRepository.findByCategoriaId(categoriaId);
    }

}