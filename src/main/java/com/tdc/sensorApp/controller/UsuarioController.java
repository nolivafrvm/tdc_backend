package com.tdc.sensorApp.controller;

import com.tdc.sensorApp.entities.Usuario;
import com.tdc.sensorApp.services.UsuarioService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    // ---------- Retrieve all Usuarios ----------

    @GetMapping
    public ResponseEntity<List<Usuario>> listAllUsuarios(){
        log.info("Retrieve all usuarios");
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // ---------- Retrieve all Usuarios by Fechas ----------

    @GetMapping("/rangoFechas")
    public ResponseEntity<List<Usuario>> listAllUsuarios(@RequestParam(defaultValue = "1", required = false) int page,
                                                             @RequestParam(defaultValue = "5", required = false) int size){
        List<Usuario> usuarios = new ArrayList<>();
        log.info("Retrieve all usuarios by fecha");
        if ((page!=0 || size!=0) && ((page>=0 && size>=0))) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("idUsuario").descending());
            usuarios = usuarioService.findAll(pageable);
        } else {
            usuarios = usuarioService.findAll();
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(usuarios);
    }

    // ---------- Retrieve last Usuario ----------

    @GetMapping("/ultimoUsuario")
    public ResponseEntity<Usuario> getLastUsuario(){
        log.info("Retrieve last usuario");
        Usuario usuario = usuarioService.findFirstByOrderByIdAsc();
        if (usuario == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // ---------- Retrieve Single Usuario ----------

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> findUsuarioById(@PathVariable("idUsuario") Long id) {
        Usuario Usuario = usuarioService.get(id);
        log.info("Retrieve single Usuario");
        if (Usuario ==null) {
            log.info("Usuario with id {} not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Usuario);
    }

    // ---------- Create Usuario -----------

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario Usuario, BindingResult result) {
        log.info("Creating a new Usuario");
        if (result.hasErrors()) {
            log.info("Error at create a new Usuario");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        log.info("Usuario Created");
        Usuario usuarioDb = usuarioService.create(Usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDb);
    }

    // ---------- Updating Usuario -----------

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") long id, @RequestBody Usuario Usuario) {
        log.info("Updating a Usuario");
        Usuario UsuarioDb = usuarioService.get(id);
        if (UsuarioDb ==null) {
            log.info("Unable to update Usuario, not exist id {}", id);
            return ResponseEntity.notFound().build();
        }
        Usuario.setIdUsuario(id);
        UsuarioDb = usuarioService.update(Usuario);
        return  ResponseEntity.ok(UsuarioDb);
    }

    // ---------- Deleting Usuario -----------

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable("id") long id){
        log.info("Fetch and deleting Customer with id {}",id);
        Usuario Usuario =usuarioService.get(id);
        if (Usuario ==null) {
            log.info("Can not delete non-exist entity with id {}",id);
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(Usuario);
        return ResponseEntity.ok(Usuario);
    }

    private String formatMessage(BindingResult result) {
        log.info("Checking for errors parsing objects");
        List<Map<String,String>> errors = result.getFieldErrors()
                .stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage error = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = "";
        try {
            toJson = objectMapper.writeValueAsString(error);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return toJson;
    }
    
}
