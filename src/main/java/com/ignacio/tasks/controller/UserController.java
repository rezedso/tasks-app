package com.ignacio.tasks.controller;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.UpdatePasswordRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "Endpoints related to users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @Operation(summary = "Update a user password")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated a user's password",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized: Wrong password"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request: Passwords don't match"
            )
    })
    @PutMapping("/update-password")
    public ResponseEntity<MessageDto> updatePassword(
            @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto
    ) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordRequestDto));
    }
}
