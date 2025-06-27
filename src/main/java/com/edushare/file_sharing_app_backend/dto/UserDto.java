package com.edushare.file_sharing_app_backend.dto;

import lombok.Data;

@Data
public class UserDto {
    private String studentId;
    private String username;
    private String email;
    private String fullName;
}
