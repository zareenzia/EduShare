package com.edushare.file_sharing_app_backend.security;

import com.edushare.file_sharing_app_backend.model.User;
import com.edushare.file_sharing_app_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return AuthenticatedUser.create(JwtUserData.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
//                .roleName(user.getRole() == null ? null : user.getRole().getRoleName())
                .build());
    }
}
