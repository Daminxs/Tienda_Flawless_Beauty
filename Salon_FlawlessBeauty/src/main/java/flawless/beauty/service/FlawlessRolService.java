/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.service;

/**
 *
 * Encargado para: Damian Perez
 */

// Esta clase debe hacer lo siguiente:

// Gestionar la lógica del negocio relacionada con los roles de usuario.
// Permitir consultar los diferentes roles dentro del sistema.
// Utilizar el repository de Rol para acceder a la base de datos.
// Servir como intermediario entre el controller y el repository para manejar permisos de usuarios.

import flawless.beauty.domain.FlawlessRol;
import flawless.beauty.repository.FlawlessRolRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlawlessRolService {

    private final FlawlessRolRepository rolRepository;

    public FlawlessRolService(FlawlessRolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional(readOnly = true)
    public List<FlawlessRol> getRoles() {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FlawlessRol getRol(Long idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

}
