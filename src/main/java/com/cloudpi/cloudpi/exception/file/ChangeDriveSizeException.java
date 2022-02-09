package com.cloudpi.cloudpi.exception.file;

public class ChangeDriveSizeException extends RuntimeException {
    public ChangeDriveSizeException() {
        super("Cannot assign less space than is currently used");
    }
}
