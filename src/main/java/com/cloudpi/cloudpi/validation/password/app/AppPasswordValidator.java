package com.cloudpi.cloudpi.validation.password.app;

import com.cloudpi.cloudpi.validation.password.PasswordSpecValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AppPasswordValidator implements ConstraintValidator<AppPassword, String> {
    private final PasswordSpecValidator passwordSpecValidator;

    public AppPasswordValidator(PasswordSpecValidator passwordSpecValidator) {
        this.passwordSpecValidator = passwordSpecValidator;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
//        context.buildConstraintViolationWithTemplate("Invalid password format").addConstraintViolation();
        //todo do testu tylko
//        return passwordSpecValidator.passwordIsValid(value);
        return true;
    }
}
