/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package flawless.beauty.service;

/**
 *
 * Encargado para: Damian Perez
 */

import flawless.beauty.domain.FlawlessCategoria;
import flawless.beauty.repository.FlawlessCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FlawlessCategoriaService {

    @Autowired
    private FlawlessCategoriaRepository categoriaRepository;

    public List<FlawlessCategoria> getCategorias() {
        return categoriaRepository.findAll();
    }

    public FlawlessCategoria getCategoria(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public void save(FlawlessCategoria categoria) {
        categoriaRepository.save(categoria);
    }

    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    public List<FlawlessCategoria> getPorTipo(String tipo) {
        return categoriaRepository.findByTipo(tipo);
    }
}