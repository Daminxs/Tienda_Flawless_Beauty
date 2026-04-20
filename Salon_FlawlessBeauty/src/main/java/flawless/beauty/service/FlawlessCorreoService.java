/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flawless.beauty.service;

/**
 *
 * Encargado para: Damian Perez
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class FlawlessCorreoService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String destino, String asunto, String contenido) {

        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(destino);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);

        mailSender.send(mensaje);
    }
}
