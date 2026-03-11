/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.service;

/**
 *
 * Encargado para: Daniela Navarro
 */

// Esta interfaz debe hacer lo siguiente:

//

import flawless.beauty.domain.FlawlessUsuario;
import flawless.beauty.repository.FlawlessUsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlawlessUsuarioService {

    private final FlawlessUsuarioRepository usuarioRepository;

    public FlawlessUsuarioService(FlawlessUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<FlawlessUsuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FlawlessUsuario getUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    @Transactional
    public void save(FlawlessUsuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

}