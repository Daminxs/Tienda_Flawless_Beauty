/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.domain;

/**
 *
 * Encargado para: Damian Perez
 */

// Esta clase debe hacer lo siguiente:

// Representar la entidad Categoria dentro del sistema.
// Esta clase se conecta con la tabla categoria de la base de datos.
// Contiene la información necesaria para clasificar los servicios del salón.
// Define atributos como el id de la categoría y su nombre.
// Permite organizar los servicios según su tipo, por ejemplo uñas, pestañas o maquillaje.

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="categoria_producto")
public class FlawlessCategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

}