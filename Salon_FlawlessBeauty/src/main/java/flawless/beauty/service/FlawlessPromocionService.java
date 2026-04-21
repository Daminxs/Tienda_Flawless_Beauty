package flawless.beauty.service;

import flawless.beauty.domain.FlawlessPromocion;
import flawless.beauty.repository.FlawlessPromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Encargado para: Monica Garcia
 */
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