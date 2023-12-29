package com.ignacio.tasks.auth.controller;

import com.ignacio.tasks.auth.dto.request.RefreshTokenRequestDto;
import com.ignacio.tasks.auth.dto.request.UserLoginRequestDto;
import com.ignacio.tasks.auth.dto.request.UserRegisterRequestDto;
import com.ignacio.tasks.auth.dto.response.LoginResponseDto;
import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.auth.dto.response.TokenRefreshResponseDto;
import com.ignacio.tasks.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Authentication", description = "Endpoints related to user authentication")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully registered",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username or email already exists")
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageDto> register(
            @RequestPart("user") @Valid UserRegisterRequestDto userRegisterRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(userRegisterRequestDto, file));
    }

    @Operation(summary = "Log a user in")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully logged in",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized: Bad credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid UserLoginRequestDto userLoginRequestDto
    ) {
        return ResponseEntity.ok(authenticationService.login(userLoginRequestDto));
    }

    @Operation(summary = "Log a user out")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User successfully logged out",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"),
    })
    @PostMapping("/logout")
    public ResponseEntity<MessageDto> logout() {
        return authenticationService.logout();
    }

    @Operation(summary = "Refresh JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token successfully refreshed",
                    content = {@Content(
                            mediaType = "application/json", schema = @Schema(implementation = TokenRefreshResponseDto.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Refresh token is not in database"),
    })
    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponseDto> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDto request) throws IOException {
        return authenticationService.refreshToken(request);
    }
}
