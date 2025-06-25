package com.edushare.file_sharing_app_backend.model;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String email;
    private String username;
    private String password;
    private String studentId;
}
