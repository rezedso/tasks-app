package com.ignacio.tasks.repository;

import com.ignacio.tasks.entity.Task;
import com.ignacio.tasks.enumeration.EPriority;
import com.ignacio.tasks.enumeration.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByBoardId(Long boardId);

    List<Task> findByEndDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Task t WHERE " +
            "(:name IS NULL OR t.name LIKE %:name%) AND " +
            "(:description IS NULL OR t.description LIKE %:description%) AND " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "(:endDate IS NULL OR t.endDate >= :endDate) AND " +
            "(:authorName IS NULL OR t.author.username LIKE %:authorName%) AND " +
            "(:boardName IS NULL OR t.board.name LIKE %:boardName%)")
    List<Task> findFilteredTasks(
            @Param("name") String name,
            @Param("description") String description,
            @Param("status") EStatus status,
            @Param("priority") EPriority priority,
            @Param("endDate") LocalDateTime endDate,
            @Param("authorName") String authorName,
            @Param("boardName") String boardName
    );
}
