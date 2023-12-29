package com.ignacio.tasks.repository;

import com.ignacio.tasks.entity.Comment;
import com.ignacio.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByTask(Task task);
}
