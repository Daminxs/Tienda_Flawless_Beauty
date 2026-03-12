/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.domain;

/**
 *
 * Encargado para: Jose Ulate
 */

// Esta clase debe hacer lo siguiente:

// Representar la entidad Producto dentro del sistema.
// Esta clase se conecta con la tabla producto de la base de datos.
// Contiene la información de los productos disponibles en la tienda del salón.
// Define atributos como id, nombre, descripción, precio y stock.
// Permite manejar la información de los productos que el usuario puede ver y reservar.

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class FlawlessProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull
    private Double precio;

    @NotNull
    private Integer stock;

    private String imagen;

    @ManyToOne
    @JoinColumn(name="categoria_id")
    private FlawlessCategoria categoria;

}