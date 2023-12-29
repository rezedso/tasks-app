package com.ignacio.tasks.service;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.UpdatePasswordRequestDto;

public interface IUserService {
    MessageDto updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto);
}
