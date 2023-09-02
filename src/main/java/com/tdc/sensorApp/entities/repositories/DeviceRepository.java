package com.tdc.sensorApp.entities.repositories;

import com.tdc.sensorApp.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
