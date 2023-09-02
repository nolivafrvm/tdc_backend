package com.tdc.sensorApp.controller;

import com.tdc.sensorApp.entities.Dato;
import com.tdc.sensorApp.services.DatoService;
import com.tdc.sensorApp.services.socket.SocketTcp;
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
@RequestMapping("/api/dato")
@RequiredArgsConstructor
public class DatoController {

    private final DatoService datoService;

    // ---------- Retrieve all Datos ----------

    @GetMapping
    public ResponseEntity<List<Dato>> listAllDatos() {
        log.info("Retrieve all datos");
        List<Dato> datos = datoService.findAll();
        if (datos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(datos);
    }

    // ---------- Retrieve all Datos by Fechas ----------

    @GetMapping("/rangoFechas")
    public ResponseEntity<List<Dato>> listAllDatosByFecha(@RequestParam String fechaInicio, @RequestParam String fechaFin,
                                                          @RequestParam(defaultValue = "1", required = false) int page,
                                                          @RequestParam(defaultValue = "5", required = false) int size) {
        List<Dato> datos = new ArrayList<>();
        log.info("Retrieve all datos by fecha");
        if ((page != 0 || size != 0) && ((page >= 0 && size >= 0))) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("idDato").descending());
            datos = datoService.findAllByFechaBetween(LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_OFFSET_DATE_TIME), LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_OFFSET_DATE_TIME), pageable);
        } else {
            datos = datoService.findAllByFechaBetween(LocalDateTime.parse(fechaInicio, DateTimeFormatter.ISO_OFFSET_DATE_TIME), LocalDateTime.parse(fechaFin, DateTimeFormatter.ISO_OFFSET_DATE_TIME), null);
            if (datos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(datos);
    }

    // ---------- Retrieve last Dato ----------

    @GetMapping("/ultimoDato")
    public ResponseEntity<Dato> getLastDato() {
        log.info("Retrieve last dato");
        Dato dato = datoService.findFirstByOrderByIdAsc();
        if (dato == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dato);
    }

    // ---------- Retrieve Single Dato ----------

    @GetMapping("/{idDato}")
    public ResponseEntity<Dato> findDatoById(@PathVariable("idDato") Long id) {
        Dato Dato = datoService.get(id);
        log.info("Retrieve single Dato");
        if (Dato == null) {
            log.info("Dato with id {} not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Dato);
    }

    // ---------- Create Dato -----------

    @PostMapping
    public ResponseEntity<Dato> createDato(@Valid @RequestBody Dato Dato, BindingResult result) {
        log.info("Creating a new Dato");
        if (result.hasErrors()) {
            log.info("Error at create a new Dato");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
        }
        log.info("Dato Created");
        Dato datoDb = datoService.create(Dato);
        return ResponseEntity.status(HttpStatus.CREATED).body(datoDb);
    }

    // ---------- Updating Dato -----------

    @PutMapping("/{id}")
    public ResponseEntity<Dato> updateDato(@PathVariable("id") long id, @RequestBody Dato Dato) {
        log.info("Updating a Dato");
        Dato DatoDb = datoService.get(id);
        if (DatoDb == null) {
            log.info("Unable to update Dato, not exist id {}", id);
            return ResponseEntity.notFound().build();
        }
        Dato.setIdDato(id);
        DatoDb = datoService.update(Dato);
        return ResponseEntity.ok(DatoDb);
    }

    // ---------- Deleting Dato -----------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Dato> deleteDato(@PathVariable("id") long id) {
        log.info("Fetch and deleting Customer with id {}", id);
        Dato Dato = datoService.get(id);
        if (Dato == null) {
            log.info("Can not delete non-exist entity with id {}", id);
            return ResponseEntity.notFound().build();
        }
        datoService.delete(Dato);
        return ResponseEntity.ok(Dato);
    }

}
