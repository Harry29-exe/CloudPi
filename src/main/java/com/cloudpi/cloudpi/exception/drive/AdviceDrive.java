package com.cloudpi.cloudpi.exception.drive;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import com.cloudpi.cloudpi.exception.path.IncorrectPathingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.*;

@ModuleExceptionHandler
public class AdviceDrive extends ExceptionHandlerPrototype {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoDrivesException.class})
    public ErrorBody handleNoDrives(NoDrivesException ex) {
        logException(ex);
        return NO_DRIVES.toErrorBody();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotEnoughDriveSpaceException.class})
    public ErrorBody handleNotEnoughSpace(NotEnoughDriveSpaceException ex) {
        logException(ex);
        return NOT_ENOUGH_DRIVE_SPACE.toErrorBody();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IncorrectPathException.class})
    public ErrorBody handleIncorrectPath(IncorrectPathingException ex) {
        logException(ex);
        return INCORRECT_PATH.toErrorBody();
    }
}
