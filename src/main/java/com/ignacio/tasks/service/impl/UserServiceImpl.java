package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.auth.dto.response.MessageDto;
import com.ignacio.tasks.dto.request.UpdatePasswordRequestDto;
import com.ignacio.tasks.repository.UserRepository;
import com.ignacio.tasks.service.IUserService;
import com.ignacio.tasks.service.IUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUtilService utilService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public MessageDto updatePassword(UpdatePasswordRequestDto request) {
        var user= utilService.getCurrentUser();

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong password.");
        }

        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Passwords don't match.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return new MessageDto("Password updated.");
    }
}
