package com.cloudpi.cloudpi.exception.user;

import com.cloudpi.cloudpi.exception.ErrorBody;

import static com.cloudpi.cloudpi.exception.ErrorCode.BAD_REQUEST;
import static com.cloudpi.cloudpi.exception.ErrorCode.NO_RESOURCE;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ModuleExceptionHandler
public class AdviceUser extends ExceptionHandlerPrototype {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotExistException.class})
    public ErrorBody handleNoUser(UserNotExistException ex) {
        logException(ex);
        return NO_RESOURCE.toErrorBody();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsersDontMatchException.class})
    public ErrorBody handleNoUser(UsersDontMatchException ex) {
        logException(ex);
        return NO_RESOURCE.toErrorBody();
    }
}
