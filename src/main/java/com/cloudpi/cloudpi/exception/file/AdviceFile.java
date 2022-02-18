package com.cloudpi.cloudpi.exception.file;

import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.*;

@ModuleExceptionHandler
public class AdviceFile extends ExceptionHandlerPrototype {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotDeleteFileException.class})
    public ErrorBody handleDeleteException(CouldNotDeleteFileException ex) {
        logException(ex);
        return DELETE_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotModifyFileException.class})
    public ErrorBody handleModifyException(CouldNotModifyFileException ex) {
        logException(ex);
        return MODIFY_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotSaveFileException.class})
    public ErrorBody handleSaveException(CouldNotSaveFileException ex) {
        logException(ex);
        return SAVE_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CouldNotReadFileException.class})
    public ErrorBody handleReadException(CouldNotReadFileException ex) {
        logException(ex);
        return READ_FAILED.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotEnoughSpaceException.class})
    public ErrorBody handleNotEnoughSpace(NotEnoughSpaceException ex) {
        logException(ex);
        return NOT_ENOUGH_SPACE.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ChangeDriveSizeException.class})
    public ErrorBody handleChangeSizeException(ChangeDriveSizeException ex) {
        logException(ex);
        return NOT_ENOUGH_SPACE.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotDirectoryException.class})
    public ErrorBody handleNotADirectory(NotDirectoryException ex) {
        logException(ex);
        return NOT_DIRECTORY.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DirectoryCompressionException.class})
    public ErrorBody handleCompressionException(DirectoryCompressionException ex) {
        logException(ex);
        return COMPRESSION_FAILED.toErrorBody();
    }
}
