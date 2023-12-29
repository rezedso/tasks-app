package com.ignacio.tasks.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    @NotEmpty(message = "Username must not be empty.")
    @Size(min = 3, max = 40, message = "Size must be between 3 and 40 characters long.")
    private String username;
    @NotEmpty(message = "Password must not be empty.")
    @Size(min = 3, max = 80, message = "Size must be between 3 and 80 characters long.")
    private String password;
}
