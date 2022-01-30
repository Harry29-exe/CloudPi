package com.cloudpi.cloudpi.exception;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.*;

@Order(0)
@RestControllerAdvice
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleExceptionHandler {

}
