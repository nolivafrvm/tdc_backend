package com.tdc.sensorApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tdc.sensorApp.entities.Dato;
import com.tdc.sensorApp.entities.repositories.DatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatoServiceImpl implements DatoService {

    public static final int TIME_TO_SAVE = 60;
    private final DatoRepository datoRepository;

    private final NovedadService novedadService;

    private final SimpMessagingTemplate messagingTemplate;


    @Value("${spring.app.diferencia.novedad}")
    private Double diferenciaNovedad;

    @Value("${spring.app.periodoLectura}")
    private Integer periodoLectura;

    @Value("${spring.app.criterioEstabilidad}")
    private Integer criterioEstabilidad;

    @Override
    public List<Dato> findAll() {
        return datoRepository.findAll();
    }

    @Override
    public Dato create(Dato dato) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            messagingTemplate.convertAndSend("/topic/nuevoDato", objectMapper.writeValueAsString(dato));
            Dato lastDato = datoRepository.findFirstByOrderByIdDatoDesc();
            if (lastDato != null) {
                if (Math.abs(Duration.between(dato.getFecha(), lastDato.getFecha()).getSeconds()) < TIME_TO_SAVE) {
                    return null;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return datoRepository.save(dato);
    }

    @Override
    public Dato update(Dato dato) {
        return datoRepository.save(dato);
    }

    @Override
    public void delete(Dato dato) {
        datoRepository.delete(dato);
    }

    @Override
    public Dato get(Long id) {
        return datoRepository.getById(id);
    }

    @Override
    public Dato findFirstByOrderByIdAsc() {
        return datoRepository.findFirstByOrderByIdDatoDesc();
    }

    @Override
    public List<Dato> findAllByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable) {
        return datoRepository.findAllByFechaBetween(fechaInicio, fechaFin, pageable);
    }

    @Override
    public List<Dato> findTop2ByIdDatoDesc() {
        return datoRepository.findTop2ByOrderByIdDatoDesc();
    }

    @Override
    public List<Dato> findTop10ByIdDatoDesc() {
        return datoRepository.findTop10ByOrderByIdDatoDesc();
    }
}
