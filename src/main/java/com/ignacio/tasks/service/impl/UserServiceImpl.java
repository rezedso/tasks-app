package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.UpdatePasswordRequestDto;
import com.ignacio.tasks.dto.request.UpdateUserRequestDto;
import com.ignacio.tasks.dto.response.UpdateUserResponseDto;
import com.ignacio.tasks.repository.UserRepository;
import com.ignacio.tasks.service.IFileUploadService;
import com.ignacio.tasks.service.IUserService;
import com.ignacio.tasks.service.IUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUtilService utilService;
    private final IFileUploadService fileUploadService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public MessageDto updatePassword(UpdatePasswordRequestDto request) {
        var user = utilService.getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password.");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords don't match.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new MessageDto("Password updated.");
    }

    @Override
    public UpdateUserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto, MultipartFile file) throws IOException {
        var user = utilService.getCurrentUser();

        if (updateUserRequestDto.getUsername() != null &&
                !Objects.equals(user.getUsername(), updateUserRequestDto.getUsername())) {
            user.setUsername(updateUserRequestDto.getUsername());
        }

        if (file != null) {
            String imageUrl = fileUploadService.uploadUserImageFile(file);
            user.setImageUrl(imageUrl);
        }

        userRepository.save(user);
        return new UpdateUserResponseDto(user.getUsername(), user.getEmail(), user.getImageUrl());
    }
}
