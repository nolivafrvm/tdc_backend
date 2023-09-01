package com.tdc.sensorApp.entities.repositories;

import com.tdc.sensorApp.entities.Role;
import com.tdc.sensorApp.security.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole eRole);

}
