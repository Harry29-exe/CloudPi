package com.cloudpi.cloudpi.exception.request;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
