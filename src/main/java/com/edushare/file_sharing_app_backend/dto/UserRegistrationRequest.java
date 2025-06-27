package com.edushare.file_sharing_app_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotNull
    private String username;
    @NotNull
    private String studentId;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
