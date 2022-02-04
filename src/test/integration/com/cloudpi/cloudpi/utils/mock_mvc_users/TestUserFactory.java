package com.cloudpi.cloudpi.utils.mock_mvc_users;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;

public class TestUserFactory implements WithSecurityContextFactory<WithUser> {

    @Override
    public SecurityContext createSecurityContext(WithUser annotation) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                annotation.username(),
                annotation.password(),
                Arrays.stream(annotation.authorities()).map(SimpleGrantedAuthority::new).toList()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
