package com.tdc.sensorApp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(getSubject(subject));
        message.setText(getBody(text));
        javaMailSender.send(message);
    }

    public String getSubject(String subject) {
        return "SensorApp - Novedades | ".concat(subject);
    }

    public String getBody(String body) {
        return ("SensorApp \n" +
                "{cuerpo} " +
                "\n " +
                "No contestar este correo\n").replace("{cuerpo}", body);
    }

}
