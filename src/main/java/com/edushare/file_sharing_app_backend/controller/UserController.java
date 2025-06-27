package com.edushare.file_sharing_app_backend.controller;


import com.edushare.file_sharing_app_backend.dto.UserDto;
import com.edushare.file_sharing_app_backend.model.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.model.UserResponse;
import com.edushare.file_sharing_app_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    public static final String API_PATH_USER_REGISTER = "/register";
    public static final String API_PATH_USER_COUNT = "/userCount";

    @PostMapping(path = API_PATH_USER_REGISTER)
    public UserResponse register(@RequestBody UserRegistrationRequest request) {
        return userService.register(request);
    }

    @GetMapping(path = API_PATH_USER_COUNT)
    public ResponseEntity<Map<String, Long>> getUserCount() {
        long count = userService.getTotalUserCount();
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }

    @GetMapping("/profile/{studentId}")
    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('ADMIN')") // Optional: restrict by role
    public ResponseEntity<UserDto> getUserProfile(@PathVariable String studentId) {
        UserDto user = userService.getUserProfileByStudentId(studentId);
        return ResponseEntity.ok(user);
    }
}



