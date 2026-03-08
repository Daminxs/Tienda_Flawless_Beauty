/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.domain;

/**
 *
 * Encargado para: Damian Perez
 */

// Esta clase representa la entidad Rol dentro del sistema:

// Se conecta con la tabla rol de la base de datos.
// Permite almacenar los diferentes tipos de roles de usuario.
// Es utilizada para gestionar los permisos y niveles de acceso dentro de la aplicación.

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="rol")
public class FlawlessRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    private String nombre;

}
