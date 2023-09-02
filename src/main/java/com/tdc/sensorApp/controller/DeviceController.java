package com.tdc.sensorApp.controller;

import com.tdc.sensorApp.entities.Device;
import com.tdc.sensorApp.services.DeviceService;
import com.tdc.sensorApp.utils.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    // ---------- Retrieve all Devices ----------

    @GetMapping
    public ResponseEntity<List<Device>> listAllDatos() {
        log.info("Retrieve all devices");
        List<Device> devices = deviceService.findAll();
        if (devices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{idDevice}")
    public ResponseEntity<Device> findDatoById(@PathVariable("idDevice") Long id) {
        Device Device = deviceService.get(id);
        log.info("Retrieve single Device");
        if (Device == null) {
            log.info("Device with id {} not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Device);
    }

    // ---------- Create Device -----------

    @PostMapping
    public ResponseEntity<Device> createDato(@Valid @RequestBody Device Device, BindingResult result) {
        log.info("Creating a new Device");
        if (result.hasErrors()) {
            log.info("Error at create a new Device");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
        }
        log.info("Device Created");
        Device datoDb = deviceService.create(Device);
        return ResponseEntity.status(HttpStatus.CREATED).body(datoDb);
    }

    // ---------- Updating Device -----------

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDato(@PathVariable("id") long id, @RequestBody Device Device) {
        log.info("Updating a Device");
        Device DatoDb = deviceService.get(id);
        if (DatoDb == null) {
            log.info("Unable to update Device, not exist id {}", id);
            return ResponseEntity.notFound().build();
        }
        Device.setIdDevice(id);
        DatoDb = deviceService.update(Device);
        return ResponseEntity.ok(DatoDb);
    }

    // ---------- Deleting Device -----------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Device> deleteDato(@PathVariable("id") long id) {
        log.info("Fetch and deleting Customer with id {}", id);
        Device Device = deviceService.get(id);
        if (Device == null) {
            log.info("Can not delete non-exist entity with id {}", id);
            return ResponseEntity.notFound().build();
        }
        deviceService.delete(Device);
        return ResponseEntity.ok(Device);
    }

}
