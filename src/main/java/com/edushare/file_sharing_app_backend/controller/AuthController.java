package com.edushare.file_sharing_app_backend.controller;

import com.edushare.file_sharing_app_backend.dto.ApiResponse;
import com.edushare.file_sharing_app_backend.dto.AuthRequest;
import com.edushare.file_sharing_app_backend.dto.AuthResponse;
import com.edushare.file_sharing_app_backend.dto.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.dto.UserResponse;
import com.edushare.file_sharing_app_backend.exception.AuthException;
import com.edushare.file_sharing_app_backend.service.AuthService;
import com.edushare.file_sharing_app_backend.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.edushare.file_sharing_app_backend.constant.ErrorMessage.INVALID_CREDENTIALS;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {

        AuthResponse response = authService.login(authRequest);

        if (Objects.isNull(response)) {
            throw new AuthException(INVALID_CREDENTIALS);
        }

        return response.toBuilder()
                .status("SUCCESS")
                .message("Login Successful")
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody @Valid UserRegistrationRequest request) {
        UserResponse userResponse = authService.register(request);
        return ResponseUtil.createdResponse(userResponse, "User registered successfully");
    }
}
