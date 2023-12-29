package com.ignacio.tasks.controller;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.CreateTaskRequestDto;
import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.request.UpdateTaskRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.dto.response.CommentResponseDto;
import com.ignacio.tasks.dto.response.ListResponseDto;
import com.ignacio.tasks.dto.response.TaskResponseDto;
import com.ignacio.tasks.enumeration.EPriority;
import com.ignacio.tasks.enumeration.EStatus;
import com.ignacio.tasks.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Task", description = "Endpoints related to tasks")
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final ITaskService taskService;

    @Operation(summary = "Get a task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found a task",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTask(
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @Operation(summary = "Get all tasks from a board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found a list of tasks",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = TaskResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<TaskResponseDto>> getBoardTasks(
            @PathVariable Long boardId
    ) {
        return ResponseEntity.ok(taskService.getBoardTasks(boardId));
    }

    @Operation(summary = "Get filtered tasks")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found a list of tasks",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = TaskResponseDto.class))
                    }
            )
    })
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getFilteredTasks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) EStatus status,
            @RequestParam(required = false) EPriority priority,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String boardName
    ) {
        return ResponseEntity.ok(taskService.getFilteredTasks(name,description,status,priority,endDate,authorName,boardName));
    }

    @Operation(summary = "Create a task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created a task",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = TaskResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Board not found"
            )
    })
    @PostMapping("/{boardId}")
    public ResponseEntity<TaskResponseDto> createTask(
            @RequestBody @Valid CreateTaskRequestDto createTaskRequestDto,
            @PathVariable("boardId") Long boardId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskRequestDto, boardId));
    }

    @Operation(summary = "Update a task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated a task",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = TaskResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @RequestBody @Valid UpdateTaskRequestDto updateTaskRequestDto,
            @PathVariable("taskId") Long taskId
    ) {
        return ResponseEntity.ok(taskService.updateTask(updateTaskRequestDto, taskId));
    }

    @Operation(summary = "Delete a task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleted a task",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId
    ) {
        taskService.deleteTask(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
