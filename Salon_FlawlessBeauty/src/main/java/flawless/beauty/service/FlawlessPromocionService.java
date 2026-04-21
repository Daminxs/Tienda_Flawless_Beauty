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
    public List<FlawlessPromocion> getPromocionesVigentes() {
        java.time.LocalDate hoy = java.time.LocalDate.now();

        return promocionRepository.findAll()
                .stream()
                .peek(p -> {
                    if (p.getFechaInicio() != null && p.getFechaFin() != null) {
                        if (hoy.isBefore(p.getFechaInicio()) || hoy.isAfter(p.getFechaFin())) {
                            p.setActivo(false);
                        } else {
                            p.setActivo(true);
                        }
                    }
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public FlawlessPromocion getPromocionById(Long id) {
        return promocionRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(FlawlessPromocion promocion) {
        promocionRepository.save(promocion);
    }

    @Transactional
    public void delete(Long id) {
        promocionRepository.deleteById(id);
    }
}