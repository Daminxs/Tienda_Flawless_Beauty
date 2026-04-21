package flawless.beauty.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Encargado para: Monica Garcia
 */
@Data
@Entity
@Table(name = "promocion")
public class FlawlessPromocion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double descuento;

    private String imagen;

    @Column(nullable = false)
    private boolean activo = true;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}