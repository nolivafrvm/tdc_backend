package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Usuario;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> findAll();
    public Usuario create(Usuario usuario);
    public Usuario update(Usuario usuario);
    public void delete(Usuario usuario);
    public Usuario get(Long id);
    public Usuario findFirstByOrderByIdAsc();
    public List<Usuario> findAll(Pageable pageable);
    
}
