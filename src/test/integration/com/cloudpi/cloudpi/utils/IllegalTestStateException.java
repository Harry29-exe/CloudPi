package com.cloudpi.cloudpi.utils;

public class IllegalTestStateException extends RuntimeException {

    public IllegalTestStateException(Throwable cause) {
        super(
                "Exception: " + cause.getClass().getSimpleName() + " has been thrown where it was not expected.",
                cause
        );
    }
}
