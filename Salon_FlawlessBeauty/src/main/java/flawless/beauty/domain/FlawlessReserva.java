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
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="reserva")
public class FlawlessReserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codigo;

    @NotNull
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private FlawlessUsuario usuario;

    @ManyToOne
    @JoinColumn(name="producto_id")
    private FlawlessProducto producto;

}