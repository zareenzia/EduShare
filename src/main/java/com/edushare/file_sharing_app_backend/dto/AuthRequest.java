package com.edushare.file_sharing_app_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    // @NotEmpty
    // private String username;
    @NotEmpty(message = "Student ID is required")
    private String studentId;
    @NotEmpty(message = "Password is required")
    private String password;
}
