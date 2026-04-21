/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flawless.beauty.repository;

/**
 *
 * Encargado para: Damian Perez
 */

import flawless.beauty.domain.FlawlessCategoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlawlessCategoriaRepository extends JpaRepository<FlawlessCategoria, Long> {

    List<FlawlessCategoria> findByTipo(String tipo);
}