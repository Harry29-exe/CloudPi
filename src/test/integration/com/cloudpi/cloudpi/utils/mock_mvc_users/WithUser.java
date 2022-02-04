package com.cloudpi.cloudpi.utils.mock_mvc_users;

import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = TestUserFactory.class, setupBefore = TestExecutionEvent.TEST_EXECUTION)
public @interface WithUser {

    String username() default "user";

    String password() default "password";

    String[] authorities() default {};

}
