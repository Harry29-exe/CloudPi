package com.cloudpi.cloudpi.exception.request;

public class NoRequiredCookieException extends RuntimeException {

    private final String cookieName;

    public NoRequiredCookieException(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getCookieName() {
        return cookieName;
    }
}
