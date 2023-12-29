package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.repository.UserRepository;
import com.ignacio.tasks.service.IUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilServiceImpl implements IUtilService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).get();
    }
}
