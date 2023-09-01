package com.tdc.sensorApp.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_novedad")
public class Novedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNovedad;

    private String valorActual;

    private String valorAnterior;

    private Double diferencia;

    private LocalDateTime fecha;

    private String descripcion;

}
