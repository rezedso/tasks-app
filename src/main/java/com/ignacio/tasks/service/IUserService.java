package com.ignacio.tasks.service;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.UpdatePasswordRequestDto;
import com.ignacio.tasks.dto.request.UpdateUserRequestDto;
import com.ignacio.tasks.dto.response.UpdateUserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    MessageDto updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto);
    UpdateUserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto, MultipartFile file) throws IOException;
}
