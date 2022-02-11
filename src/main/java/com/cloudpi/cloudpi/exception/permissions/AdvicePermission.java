package com.cloudpi.cloudpi.exception.permissions;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import com.cloudpi.cloudpi.exception.file.CouldNotDeleteFileException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.*;

@ModuleExceptionHandler
public class AdvicePermission extends ExceptionHandlerPrototype {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PermissionAlreadyExistsException.class})
    public ErrorBody handleDeleteException(PermissionAlreadyExistsException ex) {
        logException(ex);
        return PERMISSION_ALREADY_EXISTS.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoSuchPermissionException.class})
    public ErrorBody handleDeleteException(NoSuchPermissionException ex) {
        logException(ex);
        return NO_SUCH_PERMISSION.toErrorBody();
    }
}
