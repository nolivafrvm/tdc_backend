package com.tdc.sensorApp.services.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AppWebsocketHandler extends TextWebSocketHandler {

    // Lista de sesiones WebSocket activas para enviar mensajes a todos los clientes
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Conexión WebSocket establecida");

        // Agrega la sesión a la lista de sesiones activas
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Se ejecuta cuando se recibe un mensaje WebSocket de un cliente
        String receivedMessage = message.getPayload();

        // Puedes procesar el mensaje aquí si es necesario

        // Enviar el mensaje recibido de vuelta al cliente que lo envió (opcional)
        session.sendMessage(new TextMessage("Recibiste: " + receivedMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Se ejecuta cuando un cliente cierra la conexión WebSocket
        System.out.println("Conexión WebSocket cerrada. Razón: " + status);

        // Remueve la sesión de la lista de sesiones activas
        sessions.remove(session);
    }

    // Método para enviar un mensaje a todos los clientes WebSocket
    public void sendMessageToClients(String message) {
        System.out.println("Enviando un mensaje al WebSocket");

        for (WebSocketSession session : sessions) {
            try {
                // Envia el mensaje a cada sesión activa
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
