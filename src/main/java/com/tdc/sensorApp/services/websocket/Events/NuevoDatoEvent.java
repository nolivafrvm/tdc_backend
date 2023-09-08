package com.tdc.sensorApp.services.websocket.Events;

import org.springframework.context.ApplicationEvent;

public class NuevoDatoEvent extends ApplicationEvent {

    public NuevoDatoEvent(Object source) {
        super(source);
    }
}
