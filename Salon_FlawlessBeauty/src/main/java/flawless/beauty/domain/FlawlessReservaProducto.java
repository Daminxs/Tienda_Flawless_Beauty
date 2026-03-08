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

// Representar la entidad ReservaProducto dentro del sistema.
// Esta clase se conecta con la tabla reserva_producto de la base de datos.
// Contiene la información de las reservas de productos realizadas por los usuarios.
// Define atributos como id, nombre del cliente, producto seleccionado y cantidad.
// Permite registrar cuando un usuario realiza una reserva de un producto.

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "reserva_producto")
public class FlawlessReservaProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer cantidad;

    // Relación con producto
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private FlawlessProducto producto;

}
