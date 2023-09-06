package com.tdc.sensorApp.controller;

import com.tdc.sensorApp.entities.Device;
import com.tdc.sensorApp.services.DeviceService;
import com.tdc.sensorApp.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dispositivo")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    // ---------- Retrieve all Devices ----------

    @GetMapping
    public ResponseEntity<List<Device>> listAllDevices() {
        log.info("Retrieve all devices");
        List<Device> devices = deviceService.findAll();
        if (devices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{idDevice}")
    public ResponseEntity<Device> findDeviceById(@PathVariable("idDevice") String idDevice) {
        Device device = deviceService.get(Long.valueOf(idDevice));
        log.info("Retrieve single Device");
        if (device == null) {
            log.info("Device with id {} not found", idDevice);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(device);
    }

    // Configurar dispositivo
    @GetMapping("/configurar/{idDevice}/{action}")
    public ResponseEntity<Device> configurarDispositivo(@PathVariable("idDevice") String idDevice, @PathVariable("action") String action) {
        Device device = deviceService.get(Long.valueOf(idDevice));
        log.info("Retrieve single Device");
        if (device == null) {
            log.info("Device with id {} not found", idDevice);
            return ResponseEntity.notFound().build();
        }
        deviceService.controlarDispositivo(device, action);
        ResponseEntity.ok().build();

        return ResponseEntity.ok(device);
    }


    // ---------- Create Device -----------

    @PostMapping
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device Device, BindingResult result) {
        log.info("Creating a new Device");
        if (result.hasErrors()) {
            log.info("Error at create a new Device");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
        }
        log.info("Device Created");
        Device deviceDb = deviceService.create(Device);
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDb);
    }

    // ---------- Updating Device -----------

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") long id, @RequestBody Device device) {
        log.info("Updating a Device");
        Device deviceDb = deviceService.get(id);
        if (deviceDb == null) {
            log.info("Unable to update Device, not exist id {}", id);
            return ResponseEntity.notFound().build();
        }
        device.setIdDevice(id);
        deviceDb = deviceService.update(device);
        return ResponseEntity.ok(deviceDb);
    }

    // ---------- Deleting Device -----------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Device> deleteDevice(@PathVariable("id") long id) {
        log.info("Fetch and deleting Customer with id {}", id);
        Device device = deviceService.get(id);
        if (device == null) {
            log.info("Can not delete non-exist entity with id {}", id);
            return ResponseEntity.notFound().build();
        }
        deviceService.delete(device);
        return ResponseEntity.ok(device);
    }

}
