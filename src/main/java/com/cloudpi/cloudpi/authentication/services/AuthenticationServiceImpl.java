package com.cloudpi.cloudpi.authentication.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authManager;

    public AuthenticationServiceImpl(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public Authentication authenticate(String username, String password) {
        var auth = new UsernamePasswordAuthenticationToken(
                username,
                password,
                List.of()
        );
        return authManager.authenticate(auth);
    }

}
