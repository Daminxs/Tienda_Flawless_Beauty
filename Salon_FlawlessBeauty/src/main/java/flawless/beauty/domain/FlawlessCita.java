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

// Esta clase representa una cita dentro del sistema Flawless Beauty.
// Se encarga de almacenar la información relacionada con las reservas
// realizadas por los usuarios, como el servicio solicitado, la fecha,
// la hora y el correo del cliente que agenda la cita.

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
@Table(name="cita")
public class FlawlessCita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombreCliente;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name="servicio_id")
    private FlawlessServicio servicio;

}