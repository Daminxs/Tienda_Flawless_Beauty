package flawless.beauty.service;

/**
 * Encargado para: Monica Garcia
 */

import flawless.beauty.domain.FlawlessPromocion;
import flawless.beauty.repository.FlawlessPromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlawlessPromocionService {

    @Autowired
    private FlawlessPromocionRepository promocionRepository;

    @Transactional(readOnly = true)
    public List<FlawlessPromocion> getPromociones() {
        return promocionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<FlawlessPromocion> getPromocionesActivas() {
        return promocionRepository.findByActivoTrue();
    }

    @Transactional(readOnly = true)
    public List<FlawlessPromocion> filtrarPromociones(String filtro, String tipo) {

        LocalDate hoy = LocalDate.now();

        return promocionRepository.findAll()
                .stream()

                // validar fechas
                .filter(p -> {
                    if (p.getFechaInicio() == null || p.getFechaFin() == null) {
                        return true;
                    }
                    return !(hoy.isBefore(p.getFechaInicio()) || hoy.isAfter(p.getFechaFin()));
                })

                // filtro texto
                .filter(p -> {
                    if (filtro == null || filtro.isBlank()) return true;

                    String texto = filtro.toLowerCase();

                    return (p.getTitulo() != null && p.getTitulo().toLowerCase().contains(texto))
                            || (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(texto));
                })

                // filtro tipo
                .filter(p -> {
                    if (tipo == null || tipo.isBlank()) return true;
                    return p.getTipo() != null
                            && p.getTipo().equalsIgnoreCase(tipo);
                })

                .toList();
    }

    @Transactional(readOnly = true)
    public FlawlessPromocion getPromocionById(Long id) {
        return promocionRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(FlawlessPromocion promocion) {

        // LIMPIAR DATOS SEGÚN TIPO (EVITA ERRORES)
        if ("SERVICIO".equals(promocion.getTipo())) {
            promocion.setProducto(null);
            promocion.setCantidadMinima(null);
        }

        if ("PRODUCTO".equals(promocion.getTipo())) {
            promocion.setServicio(null);
        }

        promocionRepository.save(promocion);
    }

    @Transactional
    public void delete(Long id) {
        promocionRepository.deleteById(id);
    }

    // PRECIO (INCLUYE 2x1)
    public Double calcularPrecioFinal(FlawlessPromocion promo) {

        Double precioBase = 0.0;

        // SERVICIO
        if ("SERVICIO".equals(promo.getTipo()) && promo.getServicio() != null) {
            precioBase = promo.getServicio().getPrecio();
        }

        // PRODUCTO
        if ("PRODUCTO".equals(promo.getTipo()) && promo.getProducto() != null) {
            precioBase = promo.getProducto().getPrecio();
        }

        // CASO 2x1 (o combos tipo 3x2, etc.)
        if ("PRODUCTO".equals(promo.getTipo())
                && promo.getCantidadMinima() != null
                && promo.getCantidadMinima() > 1) {

            // ejemplo:
            // cantidadMinima = 2 → 2x1
            // cantidadMinima = 3 → 3x2

            int cantidad = promo.getCantidadMinima();

            // pagas (cantidad - 1)
            double total = precioBase * (cantidad - 1);

            // precio unitario promedio
            return total / cantidad;
        }

        // DESCUENTO NORMAL
        if (promo.getDescuento() != null) {
            return precioBase - (precioBase * (promo.getDescuento() / 100));
        }

        return precioBase;
    }
}