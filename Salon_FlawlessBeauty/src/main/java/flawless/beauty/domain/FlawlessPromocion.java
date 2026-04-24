package flawless.beauty.domain;

/**
 * Encargado para: Monica Garcia
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

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
    @Column(nullable = false, length = 20)
    private String tipo; // SERVICIO o PRODUCTO

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double descuento;

    private String imagen;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    // RELACIONES BD
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private FlawlessCategoria categoria;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private FlawlessServicio servicio;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private FlawlessProducto producto;

    // SOLO PRODUCTOS
    @Column(name = "cantidad_minima")
    private Integer cantidadMinima;
    
    @Transient
    private Double precioCalculado;
    
}