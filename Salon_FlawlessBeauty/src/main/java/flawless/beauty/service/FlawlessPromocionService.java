/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.service;

import flawless.beauty.domain.FlawlessPromocion;
import flawless.beauty.repository.FlawlessPromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Encargado para: Monica Garcia
 */

// Esta clase debe hacer lo siguiente:

// Gestionar la lógica del negocio relacionada con las reservas de productos.
// Permitir registrar las reservas de productos realizadas por los usuarios.
// Utilizar el repository de ReservaProducto para guardar la información en la base de datos.
// Servir como intermediario entre el controller y el repository para gestionar las reservas de productos.

@Service
public class FlawlessPromocionService {

    @Autowired
    private FlawlessPromocionRepository promocionRepository;

    @Transactional(readOnly = true)
    public List<FlawlessPromocion> getPromociones() {
        return promocionRepository.findAll();
    }

    @Transactional
    public void save(FlawlessPromocion promocion) {
        promocionRepository.save(promocion);
    }
}