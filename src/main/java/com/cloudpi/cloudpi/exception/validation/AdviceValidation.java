package com.cloudpi.cloudpi.exception.validation;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

import static com.cloudpi.cloudpi.exception.ErrorCode.INVALID_VALIDATION;

@ModuleExceptionHandler
public class AdviceValidation extends ExceptionHandlerPrototype {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ErrorBody handleJavaxValidationExceptions(ValidationException ex) {
        logException(ex);
        return INVALID_VALIDATION.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorBody handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        logException(ex);
        return INVALID_VALIDATION.toErrorBody();
    }


}