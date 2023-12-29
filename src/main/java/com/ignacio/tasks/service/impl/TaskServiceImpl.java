package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.dto.request.CreateTaskRequestDto;
import com.ignacio.tasks.dto.request.UpdateTaskRequestDto;
import com.ignacio.tasks.dto.response.TaskResponseDto;
import com.ignacio.tasks.entity.Board;
import com.ignacio.tasks.entity.Task;
import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.enumeration.EAction;
import com.ignacio.tasks.enumeration.EEntityType;
import com.ignacio.tasks.enumeration.EPriority;
import com.ignacio.tasks.enumeration.EStatus;
import com.ignacio.tasks.exception.ResourceNotFoundException;
import com.ignacio.tasks.repository.BoardRepository;
import com.ignacio.tasks.repository.TaskRepository;
import com.ignacio.tasks.service.IAuditLogService;
import com.ignacio.tasks.service.ITaskService;
import com.ignacio.tasks.service.IUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final IUtilService utilService;
    private final IAuditLogService auditLogService;
    private final ModelMapper modelMapper;

    public TaskResponseDto getTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task was not found."));

        return modelMapper.map(task, TaskResponseDto.class);
    }

    public List<TaskResponseDto> getBoardTasks(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board was not found."));

        List<Task> boardTasks = taskRepository.findByBoardId(boardId);

        return boardTasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).toList();
    }

    public TaskResponseDto createTask(
            CreateTaskRequestDto createTaskRequestDto, @PathVariable Long boardId) {
        User user = utilService.getCurrentUser();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board was not found."));

        LocalDateTime formattedEndDate = null;
        if (createTaskRequestDto.getEndDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            formattedEndDate = LocalDateTime.parse(createTaskRequestDto.getEndDate(), formatter);
        }

        Task newTask = Task.builder()
                .author(user)
                .board(board)
                .name(createTaskRequestDto.getName())
                .description(createTaskRequestDto.getDescription())
                .endDate(formattedEndDate)
                .status(EStatus.valueOf(createTaskRequestDto.getStatus()))
                .priority(EPriority.valueOf(createTaskRequestDto.getPriority()))
                .build();

        taskRepository.save(newTask);

        auditLogService.createAuditLog(newTask.getId(), EEntityType.TASK, EAction.CREATE, newTask.getName());
        return modelMapper.map(newTask, TaskResponseDto.class);
    }

    @Transactional
    public TaskResponseDto updateTask(UpdateTaskRequestDto updateTaskRequestDto, Long taskId) {
        Task taskToUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task was not found."));

        if (updateTaskRequestDto.getName() != null &&
                !taskToUpdate.getName().equals(updateTaskRequestDto.getName())) {
            taskToUpdate.setName(updateTaskRequestDto.getName());
        }

        if (updateTaskRequestDto.getDescription() != null &&
                !taskToUpdate.getDescription().equals(updateTaskRequestDto.getDescription())) {
            taskToUpdate.setDescription(updateTaskRequestDto.getDescription());
        }

        if (updateTaskRequestDto.getStatus() != null &&
                !taskToUpdate.getStatus().equals(EStatus.valueOf(updateTaskRequestDto.getStatus()))) {
            taskToUpdate.setStatus(EStatus.valueOf(updateTaskRequestDto.getStatus()));
        }

        if (updateTaskRequestDto.getPriority() != null &&
                !taskToUpdate.getPriority().equals(EPriority.valueOf(updateTaskRequestDto.getPriority()))) {
            taskToUpdate.setPriority(EPriority.valueOf(updateTaskRequestDto.getPriority()));
        }

        if (updateTaskRequestDto.getEndDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime formattedEndDatE = LocalDateTime.parse(updateTaskRequestDto.getEndDate(), formatter);

            taskToUpdate.setEndDate(formattedEndDatE);
        }

        auditLogService.createAuditLog(taskToUpdate.getId(), EEntityType.TASK, EAction.UPDATE, taskToUpdate.getName());
        return modelMapper.map(taskToUpdate, TaskResponseDto.class);
    }

    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
        taskRepository.deleteById(taskId);
        auditLogService.createAuditLog(taskId, EEntityType.TASK, EAction.DELETE);
    }

    public List<Task> findTasksDueSoon(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByEndDateBetween(start, end);
    }

    public List<TaskResponseDto> getFilteredTasks(String name, String description, EStatus status, EPriority priority, LocalDateTime endDate, String authorName, String boardName) {
        List<Task> tasks = taskRepository.findFilteredTasks(name, description, status, priority, endDate, authorName, boardName);

        return tasks.stream().map(task -> modelMapper.map(task, TaskResponseDto.class)).collect(Collectors.toList());
    }
}
