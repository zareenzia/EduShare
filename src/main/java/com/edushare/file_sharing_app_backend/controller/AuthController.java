package com.edushare.file_sharing_app_backend.controller;

import com.edushare.file_sharing_app_backend.dto.AuthRequest;
import com.edushare.file_sharing_app_backend.dto.AuthResponse;
import com.edushare.file_sharing_app_backend.exception.AuthException;
import com.edushare.file_sharing_app_backend.model.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.model.UserResponse;
import com.edushare.file_sharing_app_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.edushare.file_sharing_app_backend.constant.ErrorMessage.INVALID_CREDENTIALS;
import static com.edushare.file_sharing_app_backend.constant.ErrorMessage.USERNAME_PASSWORD_CAN_NOT_BE_NULL;
import static com.edushare.file_sharing_app_backend.util.ResponseUtil.successResponse;

@RestController()
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody @Validated AuthRequest authRequest) {

        if (Objects.isNull(authRequest.getUsername()) || Objects.isNull(authRequest.getPassword())) {
            throw new AuthException(USERNAME_PASSWORD_CAN_NOT_BE_NULL);
        }
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
    public ResponseEntity<Object> register(@RequestBody @Validated UserRegistrationRequest request) {
        UserResponse userResponse = authService.register(request);
        return successResponse("User registered successfully", userResponse, HttpStatus.CREATED);
    }
}
