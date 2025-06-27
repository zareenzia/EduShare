package com.edushare.file_sharing_app_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AuthResponse {
    private String status;
    private String message;
    private String token;
    private String username;
    private String studentId;
//    private String roleName;
    private String email;

}
