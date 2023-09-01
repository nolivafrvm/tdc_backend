package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Dato;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DatoService {

    public List<Dato> findAll();

    public Dato create(Dato dato);

    public Dato update(Dato dato);

    public void delete(Dato dato);

    public Dato get(Long id);

    public Dato findFirstByOrderByIdAsc();

    public List<Dato> findAllByFechaBetween(LocalDateTime fechaInicio,
                                            LocalDateTime fechaFin, Pageable pageable);

    List<Dato> findTop2ByIdDatoDesc();

    List<Dato> findTop10ByIdDatoDesc();

}
