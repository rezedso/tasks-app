package com.ignacio.tasks.controller;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.CreateBoardRequestDto;
import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.service.IBoardService;
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

@Tag(name = "Board", description = "Endpoints related to boards")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final IBoardService boardService;

    @Operation(summary = "Create a board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created a board",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = BoardResponseDto.class))
                    }
            )
    })
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(
            @RequestBody @Valid CreateBoardRequestDto createBoardRequestDto
    ) {
        return ResponseEntity.ok(boardService.createBoard(createBoardRequestDto));
    }

    @Operation(summary = "Update a board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Created a board",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = BoardResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Board not found"
            )
    })
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(
            @RequestBody @Valid UpdateListRequestDto updateListRequestDto,
            @PathVariable("boardId") Long boardId
    ) {
        return ResponseEntity.ok(boardService.updateBoard(updateListRequestDto, boardId));
    }

    @Operation(summary = "Delete a board")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleted a board",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Board not found"
            )
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long boardId
    ) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
