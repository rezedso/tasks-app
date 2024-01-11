package com.ignacio.tasks.controller;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.UpdatePasswordRequestDto;
import com.ignacio.tasks.dto.request.UpdateUserRequestDto;
import com.ignacio.tasks.dto.response.BoardResponseDto;
import com.ignacio.tasks.dto.response.UpdateUserResponseDto;
import com.ignacio.tasks.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "User", description = "Endpoints related to users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @Operation(summary = "Update a user's information")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated a user's information",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = UpdateUserResponseDto.class))
                    }
            )
    })
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateUserResponseDto> updateUser(
            @RequestPart(value = "user", required = false) @Parameter(schema = @Schema(type = "string", format = "binary")) @Valid UpdateUserRequestDto updateUserRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(userService.updateUser(updateUserRequestDto, file));
    }

    @Operation(summary = "Update a user's password")
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
