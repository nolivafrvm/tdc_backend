package com.tdc.sensorApp.entities.repositories;

import com.tdc.sensorApp.entities.Novedad;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NovedadRepository extends JpaRepository<Novedad, Long> {

    Novedad findFirstByOrderByIdNovedadDesc();

    List<Novedad> findAllByFechaBetween(LocalDateTime fechaInicio,
                                        LocalDateTime fechaFin, Pageable pageable);

    List<Novedad> findAllByFechaBetweenAndDescripcionIgnoreCase(LocalDateTime fechaInicio,
                                                                LocalDateTime fechaFin, Pageable pageable, String descripcion);
}
