package com.cloudpi.cloudpi.exception;

public enum ErrorCode {

    /**
     * request
     */
    BAD_REQUEST,

    /**
     * auth
     */
    AUTHENTICATION_FAILED,
    BAD_CREDENTIALS,
    UNKNOWN_DEVICE,
    ACCESS_DENIED,

    /**
     * resources
     */
    NO_RESOURCE,

    /**
     * validation
     */
    INVALID_VALIDATION,

    /**
     * files
     */
    DELETE_FAILED,
    MODIFY_FAILED,
    READ_FAILED,
    SAVE_FAILED,
    NOT_ENOUGH_SPACE;

    public ErrorBody toErrorBody() {
        return new ErrorBody(this);
    }

}
