package com.cloudpi.cloudpi.config.springdoc;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Stability {

    public static final String EARLY_DEVELOPMENT = "In early development";
    public static final String INITIAL_TESTS = "Usually very basic hand testing";
    public static final String TESTED = "Endpoint passed integration tests";
    private static final String STABILITY = "STABILITY: ";
    private static final String STABILITY_H2 = "<h2>STABILITY: </h2>";

    @Operation(
            summary = STABILITY + EARLY_DEVELOPMENT,
            description = STABILITY_H2 + EARLY_DEVELOPMENT
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EarlyDevelopment {
    }

    @Operation(
            summary = STABILITY + INITIAL_TESTS,
            description = STABILITY_H2 + INITIAL_TESTS
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface InitialTests {
    }


    @Operation(
            summary = STABILITY + TESTED,
            description = STABILITY_H2 + TESTED
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AfterTests {
    }

}
