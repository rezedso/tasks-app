package com.ignacio.tasks.controller;

import com.ignacio.tasks.dto.request.UpdateListRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.dto.response.ListResponseDto;
import com.ignacio.tasks.service.IListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "List", description = "Endpoints related to lists")
@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class ListController {
    private final IListService listService;

    @Operation(summary = "Update a list")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated a list",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = ListResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "List not found"
            )
    })
    @PutMapping("/{listId}")
    public ResponseEntity<ListResponseDto> updateList(
            @RequestBody @Valid UpdateListRequestDto updateListRequestDto,
            @PathVariable("listId") Long listId
    ) {
        return ResponseEntity.ok(listService.updateList(updateListRequestDto, listId));
    }
}
