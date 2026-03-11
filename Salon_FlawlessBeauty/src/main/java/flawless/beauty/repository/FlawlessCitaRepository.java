/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flawless.beauty.repository;

/**
 *
 * Encargado para: Damian Perez
 */


// Esta clase debe hacer lo siguiente:

// Esta interfaz se encarga de gestionar el acceso a la base de datos
// para las citas del sistema. Permite realizar operaciones como
// guardar citas, consultar citas existentes y buscar citas
// asociadas a un correo específico.

import flawless.beauty.domain.FlawlessCita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlawlessCitaRepository extends JpaRepository<FlawlessCita, Long> {

}