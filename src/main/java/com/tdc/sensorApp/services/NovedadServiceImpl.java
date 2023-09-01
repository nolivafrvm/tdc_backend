package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Novedad;
import com.tdc.sensorApp.entities.repositories.NovedadRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class NovedadServiceImpl implements NovedadService {

    private final NovedadRepository jpaNovedadRepository;

    @Override
    public List<Novedad> findAll() {
        return jpaNovedadRepository.findAll();
    }

    @Override
    public Novedad create(Novedad novedad) {
        return jpaNovedadRepository.save(novedad);
    }

    @Override
    public Novedad update(Novedad novedad) {
        return jpaNovedadRepository.save(novedad);
    }

    @Override
    public void delete(Novedad novedad) {
        jpaNovedadRepository.delete(novedad);
    }

    @Override
    public Novedad get(Long id) {
        return jpaNovedadRepository.getById(id);
    }

    @Override
    public Novedad findFirstByOrderByIdNovedadDesc() {
        return jpaNovedadRepository.findFirstByOrderByIdNovedadDesc();
    }

    @Override
    public List<Novedad> findAllByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable) {
        return jpaNovedadRepository.findAllByFechaBetween(fechaInicio, fechaFin, pageable);
    }

    @Override
    public List<Novedad> findAllByFechaBetweenAndDescripcion(LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable, String descripcion) {
        return jpaNovedadRepository.findAllByFechaBetweenAndDescripcionIgnoreCase(fechaInicio, fechaFin, pageable, descripcion);
    }
}
