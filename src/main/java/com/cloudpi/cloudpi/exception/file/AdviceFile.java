package com.cloudpi.cloudpi.exception.file;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.*;

public class AdviceFile extends ExceptionHandlerPrototype {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotDeleteFileException.class})
    public ErrorBody handleDeleteException(CouldNotDeleteFileException ex) {
        logException(ex);
        return DELETE_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotModifyFileException.class})
    public ErrorBody handleDeleteException(CouldNotModifyFileException ex) {
        logException(ex);
        return MODIFY_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotSaveFileException.class})
    public ErrorBody handleDeleteException(CouldNotSaveFileException ex) {
        logException(ex);
        return SAVE_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotReadFileException.class})
    public ErrorBody handleDeleteException(CouldNotReadFileException ex) {
        logException(ex);
        return READ_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotEnoughSpaceException.class})
    public ErrorBody handleDeleteException(NotEnoughSpaceException ex) {
        logException(ex);
        return NOT_ENOUGH_SPACE.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ChangeDriveSizeException.class})
    public ErrorBody handleDeleteException(ChangeDriveSizeException ex) {
        logException(ex);
        return NOT_ENOUGH_SPACE.toErrorBody();
    }
}
