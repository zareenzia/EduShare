package com.edushare.file_sharing_app_backend.controller;


import com.edushare.file_sharing_app_backend.dto.ApiResponse;
import com.edushare.file_sharing_app_backend.dto.UserDto;
import com.edushare.file_sharing_app_backend.dto.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.dto.UserResponse;
import com.edushare.file_sharing_app_backend.service.UserService;
import com.edushare.file_sharing_app_backend.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    // @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('ADMIN')") // Optional: restrict by role
    public ResponseEntity<ApiResponse<UserDto>> getUserProfile(@PathVariable String studentId) {
        UserDto user = userService.getUserProfileByStudentId(studentId);
        return ResponseUtil.successResponse(user, "User profile retrieved successfully");

    }
}



