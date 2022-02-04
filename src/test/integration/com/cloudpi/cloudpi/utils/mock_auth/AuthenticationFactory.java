package com.cloudpi.cloudpi.utils.mock_auth;

import com.cloudpi.cloudpi.config.security.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticationFactory {

    public static Authentication rootUser() {

        return new UsernamePasswordAuthenticationToken(
                "root",
                "123",
                List.of(new SimpleGrantedAuthority(Role.admin))
        );
    }

}
