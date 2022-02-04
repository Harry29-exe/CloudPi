package com.cloudpi.cloudpi.exception.user;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("There is no such user");
    }
}
