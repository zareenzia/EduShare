package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.dto.AuthRequest;
import com.edushare.file_sharing_app_backend.dto.AuthResponse;
import com.edushare.file_sharing_app_backend.exception.InvalidCredentialException;
import com.edushare.file_sharing_app_backend.model.User;
import com.edushare.file_sharing_app_backend.repository.UserRepository;
import com.edushare.file_sharing_app_backend.security.JwtTokenUtil;
import com.edushare.file_sharing_app_backend.security.JwtUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.edushare.file_sharing_app_backend.constant.ErrorMessage.INVALID_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthResponse login(AuthRequest authRequest) {

        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(() -> new RuntimeException("Invalid credentials."));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException(INVALID_CREDENTIALS);
        }

        JwtUserData userData = JwtUserData.builder()
                .username(user.getUsername())
                .roleName(null) // Need to implement roles later
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        String generatedToken = jwtTokenUtil.generateToken(userData);

        return AuthResponse.builder()
                .token(generatedToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

    }

}
