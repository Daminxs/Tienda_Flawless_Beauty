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

// Permitir la comunicación entre el sistema y la base de datos para la entidad Producto.
// Se conecta con la tabla producto de la base de datos.
// Permite realizar operaciones como consultar, guardar o actualizar productos.
// Es utilizada por la capa de servicios para acceder a la información de los productos.

import flawless.beauty.domain.FlawlessProducto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlawlessProductoRepository extends JpaRepository<FlawlessProducto, Long> {
    
    List<FlawlessProducto> findByCategoriaId(Long categoriaId);
    
}
