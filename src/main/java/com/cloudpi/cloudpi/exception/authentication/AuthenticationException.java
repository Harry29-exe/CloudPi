package com.cloudpi.cloudpi.exception.authentication;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Could not authenticate");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
