package com.edushare.file_sharing_app_backend.service;

import com.edushare.file_sharing_app_backend.dto.UserDto;
import com.edushare.file_sharing_app_backend.exception.UserException;
import com.edushare.file_sharing_app_backend.model.UserRegistrationRequest;
import com.edushare.file_sharing_app_backend.model.UserLoginRequest;
import com.edushare.file_sharing_app_backend.model.UserResponse;
import com.edushare.file_sharing_app_backend.model.User;
import com.edushare.file_sharing_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setStudentId(savedUser.getStudentId());
        response.setEmail(savedUser.getEmail());
        return response;
    }

    public long getTotalUserCount() {
        return userRepository.count();
    }


    public UserDto getUserProfileByStudentId(String studentId) {
        User user = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with student ID: " + studentId));

        // Convert User entity to DTO
        UserDto dto = new UserDto();
        dto.setStudentId(user.getStudentId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}

