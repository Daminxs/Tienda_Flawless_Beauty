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

// Gestionar la lógica del negocio relacionada con los productos.
// Permitir consultar y manejar la información de los productos del salón.
// Utilizar el repository de Producto para acceder a la base de datos.
// Servir como intermediario entre el controller y el repository cuando se trabaja con productos.

import flawless.beauty.domain.FlawlessProducto;
import flawless.beauty.repository.FlawlessProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FlawlessProductoService {

    private final FlawlessProductoRepository productoRepository;

    public FlawlessProductoService(FlawlessProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<FlawlessProducto> getProductos() {
        return productoRepository.findAll();
    }

    public List<FlawlessProducto> getProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public FlawlessProducto getProducto(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void save(FlawlessProducto producto) {
        productoRepository.save(producto);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
}