package com.edushare.file_sharing_app_backend.controller;


import com.edushare.file_sharing_app_backend.model.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.model.UserLoginRequest;
import com.edushare.file_sharing_app_backend.model.UserResponse;
import com.edushare.file_sharing_app_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegistrationRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}

