/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flawless.beauty.repository;

/**
 *
 * Encargado para: Damian Perez
 */

// Esta interfaz debe hacer lo siguiente:

// Permitir la comunicación entre el sistema y la base de datos para la entidad Categoria.
// Se conecta con la tabla categoria de la base de datos.
// Permite realizar operaciones como consultar, guardar o actualizar categorías.
// Es utilizada por la capa de servicios para acceder a la información de las categorías.

import flawless.beauty.domain.FlawlessCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FlawlessCategoriaRepository extends JpaRepository<FlawlessCategoria, Long> {

    @Query(value = "SELECT * FROM categoria_servicio", nativeQuery = true)
    List<FlawlessCategoria> getCategoriasServicios();

    @Query(value = "SELECT * FROM categoria_producto", nativeQuery = true)
    List<FlawlessCategoria> getCategoriasProductos();
    
}
