package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Device;
import com.tdc.sensorApp.entities.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device create(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device update(Device device) {
        if (!deviceRepository.existsById(device.getIdDevice())) {
            System.out.println("Device does not exist");
            throw new RuntimeException();
        }
        return deviceRepository.save(device);
    }

    @Override
    public void delete(Device device) {
        deviceRepository.delete(device);
    }

    @Override
    public Device get(Long id) {
        return deviceRepository.getById(id);
    }

    @Override
    public Device findFirstByOrderByIdAsc() {
        return null;
    }
}
