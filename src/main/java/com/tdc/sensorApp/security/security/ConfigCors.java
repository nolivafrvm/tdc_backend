package com.tdc.sensorApp.security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigCors implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200") // Reemplaza con el patrón de origen de tu aplicación Angular
                .allowCredentials(false)
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
