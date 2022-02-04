package com.cloudpi.cloudpi.utils.mock_auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationSetter {

    public static void setRootAuth() {
        SecurityContextHolder
                .getContext()
                .setAuthentication(
                        AuthenticationFactory.rootUser()
                );
    }

    public static void setAuth(Authentication auth) {
        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }

    public static void clearAuth() {
        SecurityContextHolder
                .getContext()
                .setAuthentication(null);
    }

}
