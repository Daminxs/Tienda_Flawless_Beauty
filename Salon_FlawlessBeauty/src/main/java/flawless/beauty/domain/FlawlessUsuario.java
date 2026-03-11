/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.domain;

/**
 *
 * Encargado para: Daniela Navarro
 */

// Esta clase debe hacer lo siguiente:

// Representar la entidad Usuario dentro del sistema.
// Esta clase se conecta con la tabla usuario de la base de datos.
// Contiene la información de las personas registradas en el sistema.
// Define atributos como id, nombre, correo y contraseña.
// Permite gestionar el registro y acceso de los usuarios dentro del sitio web.

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class FlawlessUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Long idUsuario;

    private String nombre;

    private String correo;
    
    private String telefono;

    private String password;

    private boolean activo;

    @OneToMany
    @JoinTable(
        name="usuario_rol",
        joinColumns=@JoinColumn(name="id_usuario"),
        inverseJoinColumns=@JoinColumn(name="id_rol")
    )
    private List<FlawlessRol> roles;

}
