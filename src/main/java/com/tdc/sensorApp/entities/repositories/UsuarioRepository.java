package com.tdc.sensorApp.entities.repositories;

import com.tdc.sensorApp.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findFirstByOrderByIdUsuarioDesc();

    Page<Usuario> findAll(Pageable pageable);

    boolean existsByUsername(String usuario);

    boolean existsByEmail(String email);

    Optional<Usuario> findByUsername(String username);

}
