package com.cloudpi.cloudpi.exception.user;

public class UsersDontMatchException extends RuntimeException {
    public UsersDontMatchException() {
        super("One of provided usernames do not match");
    }
}
