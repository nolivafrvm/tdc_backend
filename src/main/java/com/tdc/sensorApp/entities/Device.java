package com.tdc.sensorApp.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_device")
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_device")
    private Long idDevice;

    private String name;
    private String description;
    private Float kp;
    private Float kd;
    private Float ki;
    private String ipaddress;
    private String gateway;
    private String subMask;
    private Integer port;
    private Float setpoint;
    private String ipaddressserver;
    private Integer portserver;
    private Long recordPeriod;
    private Integer idConfiguracion;

}
