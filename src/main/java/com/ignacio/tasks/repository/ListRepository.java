package com.ignacio.tasks.repository;

import com.ignacio.tasks.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ListRepository extends JpaRepository<List,Long> {
    Optional<List> findByName(String name);
}
