package com.edushare.file_sharing_app_backend.controller;


import com.edushare.file_sharing_app_backend.model.UserLoginRequest;
import com.edushare.file_sharing_app_backend.model.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.model.UserResponse;
import com.edushare.file_sharing_app_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    public static final String API_PATH_USER_REGISTER = "/register";
    public static final String API_PATH_USER_LOGIN = "/login";
    public static final String API_PATH_USER_COUNT = "/userCount";
    public static final String API_PATH_USER_PROFILE = "/profile/{studentId}";

    @PostMapping(path = API_PATH_USER_REGISTER)
    public UserResponse register(@RequestBody UserRegistrationRequest request) {
        return userService.register(request);
    }

    @PostMapping(path = API_PATH_USER_LOGIN)
    public UserResponse login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @GetMapping(path = API_PATH_USER_COUNT)
    public ResponseEntity<Map<String, Long>> getUserCount() {
        long count = userService.getTotalUserCount();
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }

    @GetMapping(API_PATH_USER_PROFILE)
    public ResponseEntity<UserResponse> getProfile(@PathVariable String studentId) {
        return ResponseEntity.ok(userService.getUserProfileByStudentId(studentId));
    }


}

