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
    private final String ERROR_CODE = "INPUT_VALUES_CONSTRAINT_VIOLATION";

    @ExceptionHandler({ValidationException.class})
    public ErrorBody handleJavaxValidationExceptions(ValidationException ex) {
        //todo marge to method below
        return INVALID_VALIDATION.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorBody handleArgumentNotValidException(MethodArgumentNotValidException ex) {
//        System.out.println("\n"+ex.getMessage()+"\n");
       return INVALID_VALIDATION.toErrorBody();
        //todo if controller return bad request otherwise print exception stack trace
//        boolean appendMsg = false;
//
//        Method method = ex.getParameter().getMethod();
//        if (method != null) {
//            Class<?> clazz = method.getDeclaringClass();
//            appendMsg = clazz.getAnnotation(RestController.class) != null;
//        }
//
//        if (appendMsg) {
//            List<String> notValidFieldsNames = new ArrayList<>();
//            ex.getBindingResult().getAllErrors().forEach((error) -> {
//                FieldError fieldError = (FieldError) error;
//                String fieldName = fieldError.getField();
//                notValidFieldsNames.add(fieldName);
//            });
//
//            return new ValidationErrorBody(ERROR_CODE, notValidFieldsNames);
//        }
//
//        return new ErrorBody(ERROR_CODE);
    }


}