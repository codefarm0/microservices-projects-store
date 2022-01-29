package com.gl.userservice.controller;

import com.gl.userservice.model.User;
import com.gl.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public User registerUser(@Valid @RequestBody User user){

        return userService.registerUser(user);

    }

    @GetMapping("/user/find")
    public User findByEmail(@RequestParam String email){
        return userService.findByEmail(email);
    }
}
