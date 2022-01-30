package com.cloudpi.cloudpi.exception.request;

public class NoRequiredHeaderException extends RuntimeException {

    private final String headerName;

    public NoRequiredHeaderException(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}
