package com.tdc.sensorApp.services;

import com.tdc.sensorApp.entities.Usuario;
import com.tdc.sensorApp.entities.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        Usuario usuarioDb = usuarioRepository.getById(usuario.getIdUsuario());
        if (usuarioDb==null) {
             throw new RuntimeException();
        }
        usuarioDb.setApellido(usuario.getApellido());
        usuarioDb.setNombre(usuario.getNombre());
        usuarioDb.setEmail(usuario.getEmail());
        usuarioDb.setUsername(usuario.getUsername());
        usuarioDb.setPassword(usuario.getPassword());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);         
    }

    @Override
    public Usuario get(Long id) {
        return usuarioRepository.getById(id);
    }

    @Override
    public Usuario findFirstByOrderByIdAsc() {
        return usuarioRepository.findFirstByOrderByIdUsuarioDesc();
    }

    @Override
    public List<Usuario> findAll(Pageable pageable) {
        return (List<Usuario>) usuarioRepository.findAll(pageable);
    }
}
