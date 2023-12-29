package com.ignacio.tasks.repository;

import com.ignacio.tasks.entity.Role;
import com.ignacio.tasks.enumeration.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
