package flawless.beauty.repository;

import flawless.beauty.domain.FlawlessPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Encargado para: Monica Garcia
 */
@Repository
public interface FlawlessPromocionRepository extends JpaRepository<FlawlessPromocion, Long> {
    List<FlawlessPromocion> findByActivoTrue();
}