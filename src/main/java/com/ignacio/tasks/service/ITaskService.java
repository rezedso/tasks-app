package com.ignacio.tasks.service;

import com.ignacio.tasks.dto.request.CreateTaskRequestDto;
import com.ignacio.tasks.dto.request.UpdateTaskRequestDto;
import com.ignacio.tasks.dto.response.TaskResponseDto;
import com.ignacio.tasks.entity.Task;
import com.ignacio.tasks.enumeration.EPriority;
import com.ignacio.tasks.enumeration.EStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ITaskService {
   TaskResponseDto getTask(Long taskId);
    List<TaskResponseDto>getBoardTasks(Long boardId);
    TaskResponseDto createTask(CreateTaskRequestDto createTaskRequestDto,Long boardId);
    TaskResponseDto updateTask(UpdateTaskRequestDto updateTaskRequestDto, Long taskId);
    void deleteTask(Long taskId);
    List<Task> findTasksDueSoon(LocalDateTime start, LocalDateTime end);

    List<TaskResponseDto> getFilteredTasks(String name, String description, EStatus status, EPriority priority, LocalDateTime endDate, String authorName, String boardName);
}
