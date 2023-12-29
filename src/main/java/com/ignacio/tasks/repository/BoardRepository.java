package com.ignacio.tasks.repository;

import com.ignacio.tasks.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
