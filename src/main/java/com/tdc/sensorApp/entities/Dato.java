package com.tdc.sensorApp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_dato")
public class Dato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDato;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;

    private String valor; // Temperatura actual

    private String outputPid;
    private String rpm;
    private String kp;
    private String kd;
    private String ki;
    private String setpoint;
    private String porcentaje;
    private String idConfiguration;

    @OneToOne
    private Device dispositivo;

}
