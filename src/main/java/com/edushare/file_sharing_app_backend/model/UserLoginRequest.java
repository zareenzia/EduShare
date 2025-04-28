package com.edushare.file_sharing_app_backend.model;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
