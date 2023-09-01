package com.tdc.sensorApp.entities;

import com.tdc.sensorApp.security.models.ERole;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    @Column(name="created_on", nullable = false)
    private Date createdOn;

    @Column(name="created_by", nullable = false)
    private Long createdBy;

    @Column(name="last_updated_on")
    private Date lastUpdatedOn;

    @Column(name="last_updated_by")
    private Long lastUpdatedBy;

}
