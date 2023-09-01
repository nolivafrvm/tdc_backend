package com.tdc.sensorApp.entities.repositories;

import com.tdc.sensorApp.entities.Dato;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DatoRepository extends JpaRepository<Dato, Long> {

    Dato findFirstByOrderByIdDatoDesc();

    List<Dato> findTop2ByOrderByIdDatoDesc();

    List<Dato> findTop10ByOrderByIdDatoDesc();

    List<Dato> findAllByFechaBetween(LocalDateTime fechaInicio,
                                     LocalDateTime fechaFin, Pageable pageable);

}
