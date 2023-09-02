package com.tdc.sensorApp.services.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketConfiguration {

    @Autowired
    private SocketTcp socketTcp;

    @Bean
    public void startSocketServiceInThread() {
        Thread socketThread = new Thread(() -> {
            socketTcp.startServer();
        });
        socketThread.start();
    }

}
