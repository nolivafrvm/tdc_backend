package com.tdc.sensorApp.services.websocket.Events;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NuevoDatoEventHandler {

    private final SimpMessagingTemplate messagingTemplate;

    public NuevoDatoEventHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleNuevoDatoEvent(NuevoDatoEvent event) {
        // Enviar un mensaje WebSocket a un destino específico (por ejemplo, "/topic/nuevoDato")
        messagingTemplate.convertAndSend("/topic/nuevoDato", "Se ha guardado un nuevo Dato");
    }
}
