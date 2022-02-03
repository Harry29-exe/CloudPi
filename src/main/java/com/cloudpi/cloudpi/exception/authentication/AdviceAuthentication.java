package com.cloudpi.cloudpi.exception.authentication;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cloudpi.cloudpi.exception.ErrorBody;
import com.cloudpi.cloudpi.exception.ExceptionHandlerPrototype;
import com.cloudpi.cloudpi.exception.ModuleExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.cloudpi.cloudpi.exception.ErrorCode.AUTHENTICATION_FAILED;
import static com.cloudpi.cloudpi.exception.ErrorCode.BAD_CREDENTIALS;

@ModuleExceptionHandler
public class AdviceAuthentication extends ExceptionHandlerPrototype {


    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public ErrorBody handleBadCredentials(BadCredentialsException ex) {
        logException(ex);
        return BAD_CREDENTIALS.toErrorBody();
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class, JwtValidationException.class})
    public ErrorBody handleAuthenticationException(AuthenticationException ex) {
        logException(ex);
        return AUTHENTICATION_FAILED.toErrorBody();
    }


    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            JWTCreationException.class,
            JWTDecodeException.class,
            JWTVerificationException.class})
    public ErrorBody handleJwtException(Exception ex) {
        logException(ex);
        return AUTHENTICATION_FAILED.toErrorBody();
    }


    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            DeviceNotAuthorizedException.class
    })
    public ErrorBody handleUnauthorizedDevice(DeviceNotAuthorizedException ex) {
        logException(ex);
        return AUTHENTICATION_FAILED.toErrorBody();
    }


    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnknownDeviceException.class})
    public ErrorBody handleUnknownDeviceException(UnknownDeviceException ex) {
        logException(ex);
        return AUTHENTICATION_FAILED.toErrorBody();
    }

}

