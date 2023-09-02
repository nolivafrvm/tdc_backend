package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Dato;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DatoService {

    List<Dato> findAll();

    Dato create(Dato dato);

    Dato update(Dato dato);

    void delete(Dato dato);

    Dato get(Long id);

    Dato findFirstByOrderByIdAsc();

    public List<Dato> findAllByFechaBetween(LocalDateTime fechaInicio,
                                            LocalDateTime fechaFin, Pageable pageable);

    List<Dato> findTop2ByIdDatoDesc();

    List<Dato> findTop10ByIdDatoDesc();

}
