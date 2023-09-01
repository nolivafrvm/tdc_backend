package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Novedad;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface NovedadService {

    public List<Novedad> findAll();

    public Novedad create(Novedad novedad);

    public Novedad update(Novedad novedad);

    public void delete(Novedad novedad);

    public Novedad get(Long id);

    public Novedad findFirstByOrderByIdNovedadDesc();

    public List<Novedad> findAllByFechaBetween(LocalDateTime fechaInicio,
                                               LocalDateTime fechaFin, Pageable pageable);

    List<Novedad> findAllByFechaBetweenAndDescripcion(LocalDateTime fechaInicio,
                                                      LocalDateTime fechaFin, Pageable pageable, String descripcion);

}
