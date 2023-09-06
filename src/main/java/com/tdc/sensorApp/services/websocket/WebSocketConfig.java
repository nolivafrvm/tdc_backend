package com.tdc.sensorApp.services.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/socket.io")
                .setAllowedOrigins("http://localhost:4200") // Reemplaza con el origen de tu cliente Angular
                .withSockJS();
    }


    @Bean
    public WebSocketHandler webSocketHandler() {
        return new AppWebsocketHandler();
    }
}
