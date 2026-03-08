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

// Representar la entidad ReservaServicio dentro del sistema.
// Esta clase se conecta con la tabla reserva_servicio de la base de datos.
// Contiene la información de las citas o reservas de servicios del salón.
// Define atributos como id, nombre del cliente, fecha, hora y servicio seleccionado.
// Permite registrar cuando un usuario agenda una cita para un servicio.

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
@Table(name = "reserva_servicio")
public class FlawlessReservaServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(nullable = false)
    private LocalTime hora;

    // Relación con servicio
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private FlawlessServicio servicio;
}