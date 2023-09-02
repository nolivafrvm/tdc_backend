package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Device;

import java.util.List;

public interface DeviceService {

    List<Device> findAll();

    Device create(Device device);

    Device update(Device device);

    void delete(Device device);

    Device get(Long id);

    Device findFirstByOrderByIdAsc();
}
