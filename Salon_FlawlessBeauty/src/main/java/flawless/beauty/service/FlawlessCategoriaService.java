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

// Gestionar la lógica del negocio relacionada con las categorías.
// Permitir obtener, guardar o modificar las categorías del sistema.
// Utilizar el repository de Categoria para acceder a la base de datos.
// Servir como intermediario entre el controller y el repository cuando se trabaja con categorías.

import flawless.beauty.domain.FlawlessCategoria;
import flawless.beauty.repository.FlawlessCategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlawlessCategoriaService {

    private final FlawlessCategoriaRepository categoriaRepository;

    public FlawlessCategoriaService(FlawlessCategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<FlawlessCategoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<FlawlessCategoria> getCategoria(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public void save(FlawlessCategoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

}
