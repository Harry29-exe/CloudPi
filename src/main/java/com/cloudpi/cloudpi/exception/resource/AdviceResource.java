package com.cloudpi.cloudpi.exception.resource;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.BAD_REQUEST;
import static com.cloudpi.cloudpi.exception.ErrorCode.NO_RESOURCE;
import static java.lang.String.format;

@ModuleExceptionHandler
public class AdviceResource extends ExceptionHandlerPrototype {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotExistException.class)
    public ErrorBody handleResourceNotFound(ResourceNotExistException ex) {
        return handleException(ex, NO_RESOURCE);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ErrorBody generalResourceExceptionHandler(ResourceAlreadyExistException ex) {
        return handleException(ex, NO_RESOURCE);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalNoResourceException.class})
    public ErrorBody handleIllegalNoResource(IllegalNoResourceException ex) {
        logError(
                format("IllegalNoResourceException has been thrown in class: %s in method: %s",
                        ex.getStackTrace()[0].getClassName(),
                        ex.getStackTrace()[0].getMethodName()),
                ex
        );

        return BAD_REQUEST.toErrorBody();
    }

}
