package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.dto.UserDto;
import com.edushare.file_sharing_app_backend.dto.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.dto.UserResponse;
import com.edushare.file_sharing_app_backend.exception.ResourceNotFoundException;
import com.edushare.file_sharing_app_backend.exception.UserException;
import com.edushare.file_sharing_app_backend.model.User;
import com.edushare.file_sharing_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.edushare.file_sharing_app_backend.constant.ErrorMessage.EMAIL_ALREADY_IN_USE;
import static com.edushare.file_sharing_app_backend.constant.ErrorMessage.USER_ALREADY_IN_USE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegistrationRequest request) {

        if (userRepository.findByStudentId(request.getStudentId()).isPresent()) {
            throw new UserException(USER_ALREADY_IN_USE);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserException(EMAIL_ALREADY_IN_USE);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setStudentId(request.getStudentId());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .username(savedUser.getUsername())
                .studentId(savedUser.getStudentId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .build();
    }

    public long getTotalUserCount() {
        return userRepository.count();
    }


    public UserDto getUserProfileByStudentId(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Student ID: " + studentId));

        // Convert User entity to DTO
        UserDto dto = new UserDto();
        dto.setStudentId(user.getStudentId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        return dto;
    }
}

