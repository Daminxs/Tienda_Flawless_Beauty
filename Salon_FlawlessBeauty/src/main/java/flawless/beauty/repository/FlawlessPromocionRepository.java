package flawless.beauty.repository;

/**
 * Encargado para: Monica Garcia
 */


import flawless.beauty.domain.FlawlessPromocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlawlessPromocionRepository extends JpaRepository<FlawlessPromocion, Long> {

    List<FlawlessPromocion> findByActivoTrue();

}