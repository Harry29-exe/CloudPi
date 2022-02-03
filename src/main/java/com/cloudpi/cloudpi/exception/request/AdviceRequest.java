package com.cloudpi.cloudpi.exception.request;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.BAD_REQUEST;

@ModuleExceptionHandler
public class AdviceRequest extends ExceptionHandlerPrototype {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoRequiredHeaderException.class})
    public ErrorBody handleNoRequiredHeaderException(NoRequiredHeaderException ex) {
        logException(ex);

        return BAD_REQUEST.toErrorBody();
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoRequiredCookieException.class)
    public ErrorBody handleNoRequiredCookie(NoRequiredCookieException ex) {
        logException(ex);

        return BAD_REQUEST.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRequestException.class})
    public ErrorBody handleGeneralInvalidRequestException(InvalidRequestException ex) {
        logException(ex);

        return BAD_REQUEST.toErrorBody();
    }

}
