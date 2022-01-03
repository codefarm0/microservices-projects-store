package com.gl.userservice.service;

import com.gl.userservice.exception.UserDataValidationException;
import com.gl.userservice.model.User;
import com.gl.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        user.setInsertDate(new Date());
        user.setId(UUID.randomUUID().toString());

        user.setPassword(securePassword(user.getPassword()));
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    private String securePassword(String password) {
        return null;
    }
}
