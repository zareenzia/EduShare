package com.edushare.file_sharing_app_backend.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
