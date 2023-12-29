package com.ignacio.tasks.controller;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.CommentRequestDto;
import com.ignacio.tasks.dto.request.UpdateCommentRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.dto.response.CommentResponseDto;
import com.ignacio.tasks.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "Endpoints related to comments")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @Operation(summary = "Get all comments from a task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found a list of comments",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = CommentResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<List<CommentResponseDto>> getTaskComments(
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(commentService.getTaskComments(taskId));
    }

    @Operation(summary = "Create a comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created a comment",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @PostMapping("/{taskId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long taskId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        return new ResponseEntity<>(commentService.createComment(taskId, commentRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated a comment",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = CommentResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found"
            )
    })
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto
    ) {
        return ResponseEntity.ok(commentService.updateComment(commentId, updateCommentRequestDto));
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleted a comment",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found"
            )
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageDto> deleteComment(
            @PathVariable Long commentId
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commentService.deleteComment(commentId));
    }
}

