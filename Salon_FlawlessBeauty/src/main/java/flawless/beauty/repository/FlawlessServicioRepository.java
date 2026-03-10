/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flawless.beauty.repository;

/**
 *
 * Encargado para: Jose Ulate
 */

// Esta interfaz debe hacer lo siguiente:

// Permitir la comunicación entre el sistema y la base de datos para la entidad Servicio.
// Se conecta con la tabla servicio de la base de datos.
// Permite consultar, guardar o actualizar los servicios que ofrece el salón.
// Es utilizada por la capa de servicios para acceder a la información de los servicios.

import flawless.beauty.domain.FlawlessServicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlawlessServicioRepository extends JpaRepository<FlawlessServicio, Long> {
    
    List<FlawlessServicio> findByCategoriaId(Long categoriaId);
    
}
