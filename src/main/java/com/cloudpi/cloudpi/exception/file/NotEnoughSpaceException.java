package com.cloudpi.cloudpi.exception.file;

public class NotEnoughSpaceException extends RuntimeException {
    public NotEnoughSpaceException() {
        super("There is no enough space to add a new file");
    }
}
