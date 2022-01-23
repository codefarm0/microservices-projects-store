package com.gl.userservice.service;

import com.gl.userservice.model.User;
import com.gl.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordService passwordService;

    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public User registerUser(User user) {
        user.setInsertDate(new Date());
        user.setId(UUID.randomUUID().toString());

        user.setPassword(passwordService.securePassword(user.getPassword()));
        User savedUser = userRepository.save(user);

        savedUser.setPassword("******");
        return savedUser;
    }

    private String securePassword(String password) {
        return null;
    }
}
