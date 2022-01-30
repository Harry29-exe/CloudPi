package com.cloudpi.cloudpi.exception.authentication;

public class JwtValidationException extends AuthenticationException {

    public JwtValidationException() {
        super("No authorization");
    }
}
