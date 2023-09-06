package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Device;
import com.tdc.sensorApp.entities.repositories.DeviceRepository;
import com.tdc.sensorApp.services.socket.SocketTcp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private static final String ACTION_SAVE = "1";
    private static final String ACTION_RESET = "2";
    private static final String ACTION_RELAY_ON = "3";
    private static final String ACTION_RELAY_OFF = "4";
    private static final String ACTION_GET_VALUE = "5";

    private final DeviceRepository deviceRepository;

    private final SocketTcp socketTcp;

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device create(Device device) {
        device.setIdConfiguracion(1);
        return deviceRepository.save(device);
    }

    @Override
    public Device update(Device device) {
        if (device.getIdConfiguracion() != null) {
            device.setIdConfiguracion(device.getIdConfiguracion() + 1);
        } else {
            device.setIdConfiguracion(1);
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

    @Override
    public void controlarDispositivo(Device device, String action) {
        socketTcp.controlarDispositivo(device, action);
    }


}
