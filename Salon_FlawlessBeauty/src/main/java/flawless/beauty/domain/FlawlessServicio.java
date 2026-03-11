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

// Representar la entidad Servicio dentro del sistema.
// Esta clase se conecta con la tabla servicio de la base de datos.
// Contiene la información de los servicios que ofrece el salón.
// Define atributos como id, nombre, descripción, precio y categoría.
// Permite mostrar los servicios disponibles para que el usuario pueda agendar una cita.

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="servicio")
public class FlawlessServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 255)
    private String descripcion;

    @NotNull
    private Double precio;

    // relación con categoria
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private FlawlessCategoria categoria;

}