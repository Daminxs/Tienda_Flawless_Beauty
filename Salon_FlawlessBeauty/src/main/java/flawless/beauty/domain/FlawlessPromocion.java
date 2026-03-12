/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
/**
 *
 * Encargado para: Monica Garcia
 */

// Esta clase debe hacer lo siguiente:

// Representar la entidad Promocion dentro del sistema.
// Esta clase se conecta con la tabla promocion de la base de datos.
// Contiene la información de las promociones disponibles en el salón.
// Define atributos como id, título, descripción y descuento.
// Permite mostrar las promociones que el usuario puede ver dentro del sitio web.

@Data
@Entity
@Table(name="promocion")
public class FlawlessPromocion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private double descuento;
}