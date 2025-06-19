package com.edushare.file_sharing_app_backend.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class AuthenticatedUser implements UserDetails {
    private final Long id;
    private final String userId;
    private final String email;
    private final String password;
    private final String roleName;
    private final Boolean activationStatus;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetails create(JwtUserData user) {
        List<GrantedAuthority> authorityList = List.of(user::roleName);
        return AuthenticatedUser.builder()
                .email(user.email())
                .password(user.password())
                .roleName(user.roleName())
                .authorities(authorityList)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activationStatus != null && activationStatus;
    }

}
