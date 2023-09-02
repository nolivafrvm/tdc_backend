package com.tdc.sensorApp.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDevice;

    private String name;
    private String description;
    private Float kP;
    private Float kD;
    private Float kI;
    private String ipAddress;
    private Integer port;

}
