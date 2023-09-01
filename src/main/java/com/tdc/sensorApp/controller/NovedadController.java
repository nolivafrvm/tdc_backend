package com.tdc.sensorApp.controller;

import com.tdc.sensorApp.entities.Novedad;
import com.tdc.sensorApp.services.NovedadService;
import com.tdc.sensorApp.utils.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/novedad")
@RequiredArgsConstructor
public class NovedadController {

    private final NovedadService novedadService;

    // ---------- Retrieve all Novedades ----------

    @GetMapping
    public ResponseEntity<List<Novedad>> listAllNovedades() {
        log.info("Retrieve all novedades");
        List<Novedad> novedades = novedadService.findAll();
        if (novedades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(novedades);
    }

    // ---------- Retrieve all Novedades by fecha and pagination ----------

    @GetMapping("/rangoFechas")
    public ResponseEntity<List<Novedad>> listAllNovedadesByFecha(@RequestParam String fechaInicio,
                                                                 @RequestParam String fechaFin,
                                                                 @RequestParam(defaultValue = "1", required = false) int page,
                                                                 @RequestParam(defaultValue = "5", required = false) int size) {
        List<Novedad> novedades = new ArrayList<>();
        log.info("Retrieve all novedades by fecha");
        if ((page != 0 || size != 0) && ((page >= 0 && size >= 0))) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("idNovedad").descending());
            novedades = novedadService.findAllByFechaBetween(LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_OFFSET_DATE_TIME), LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_OFFSET_DATE_TIME), pageable);
        } else {
            novedades = novedadService.findAllByFechaBetween(LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_OFFSET_DATE_TIME), LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_OFFSET_DATE_TIME), null);
            if (novedades.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(novedades);
    }

    // ---------- Retrieve all Novedades by fecha and pagination ----------

    @GetMapping("/busqueda")
    public ResponseEntity<List<Novedad>> listAllNovedadesByFechaAndDescripcion(@RequestParam String fechaInicio,
                                                                               @RequestParam String fechaFin,
                                                                               @RequestParam(defaultValue = "1", required = false) int page,
                                                                               @RequestParam(defaultValue = "5", required = false) int size,
                                                                               @RequestParam(required = false) String descripcion) {
        List<Novedad> novedades = new ArrayList<>();
        log.info("Retrieve all novedades by fecha and descripcion");
        if ((page != 0 || size != 0) && ((page >= 0 && size >= 0))) {
            log.info("Retrieve all novedades with pageable");
            Pageable pageable = PageRequest.of(page, size, Sort.by("idNovedad").descending());
            novedades = novedadService.findAllByFechaBetweenAndDescripcion(LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_OFFSET_DATE_TIME), LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_OFFSET_DATE_TIME), pageable, descripcion);
        } else {
            log.info("Retrieve all novedades");
            novedades = novedadService.findAllByFechaBetweenAndDescripcion(LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_OFFSET_DATE_TIME), LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_OFFSET_DATE_TIME), null, descripcion);
            if (novedades.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(novedades);
    }

    // ---------- Retrieve last Novedad ----------

    @GetMapping("/ultimaNovedad")
    public ResponseEntity<Novedad> getLastNovedad() {
        log.info("Retrieve last novedad");
        Novedad novedad = novedadService.findFirstByOrderByIdNovedadDesc();
        if (novedad == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(novedad);
    }

    // ---------- Retrieve Single Novedad ----------

    @GetMapping("/{idNovedad}")
    public ResponseEntity<Novedad> findNovedadById(@PathVariable("idNovedad") Long id) {
        Novedad Novedad = novedadService.get(id);
        log.info("Retrieve single Novedad");
        if (Novedad == null) {
            log.info("Novedad with id {} not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Novedad);
    }

    // ---------- Create Novedad -----------

    @PostMapping
    public ResponseEntity<Novedad> createNovedad(@Valid @RequestBody Novedad Novedad, BindingResult result) {
        log.info("Creating a new Novedad");
        if (result.hasErrors()) {
            log.info("Error at create a new Novedad");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
        }
        log.info("Novedad Created");
        Novedad datoDb = novedadService.create(Novedad);
        return ResponseEntity.status(HttpStatus.CREATED).body(datoDb);
    }

    // ---------- Updating Novedad -----------

    @PutMapping("/{id}")
    public ResponseEntity<Novedad> updateNovedad(@PathVariable("id") long id, @RequestBody Novedad Novedad) {
        log.info("Updating a Novedad");
        Novedad NovedadDb = novedadService.get(id);
        if (NovedadDb == null) {
            log.info("Unable to update Novedad, not exist id {}", id);
            return ResponseEntity.notFound().build();
        }
        Novedad.setIdNovedad(id);
        NovedadDb = novedadService.update(Novedad);
        return ResponseEntity.ok(NovedadDb);
    }

    // ---------- Deleting Novedad -----------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Novedad> deleteNovedad(@PathVariable("id") long id) {
        log.info("Fetch and deleting Customer with id {}", id);
        Novedad Novedad = novedadService.get(id);
        if (Novedad == null) {
            log.info("Can not delete non-exist entity with id {}", id);
            return ResponseEntity.notFound().build();
        }
        novedadService.delete(Novedad);
        return ResponseEntity.ok(Novedad);
    }
}
