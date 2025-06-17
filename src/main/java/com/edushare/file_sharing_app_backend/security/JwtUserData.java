package com.edushare.file_sharing_app_backend.security;

import lombok.Builder;

@Builder
public record JwtUserData(
        String username,
        String roleName,
        String email,
        String password) {
}
