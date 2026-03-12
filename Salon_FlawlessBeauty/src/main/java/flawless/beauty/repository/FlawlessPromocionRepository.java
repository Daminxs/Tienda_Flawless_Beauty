/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flawless.beauty.repository;

import flawless.beauty.domain.FlawlessPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * Encargado para: Monica Garcia
 */

// Esta interfaz debe hacer lo siguiente:

// Permitir la comunicación entre el sistema y la base de datos para la entidad Promocion.
// Se conecta con la tabla promocion de la base de datos.
// Permite consultar las promociones disponibles en el sistema.
// Es utilizada por la capa de servicios para obtener la información de las promociones.

@Repository
public interface FlawlessPromocionRepository extends JpaRepository<FlawlessPromocion, Long> {
    // Hereda todos los métodos necesarios como findAll() y save()
}