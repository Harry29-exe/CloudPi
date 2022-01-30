package com.cloudpi.cloudpi.config.springdoc;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class NotImplemented {

    public static final String notImplemented = "Not implemented";
    public static final String methodNotImplemented1 =
            "<h2><font color=\"#911\">" +
                    "Method not implemented</font> [priority: high]</h2>\n";
    public static final String methodNotImplemented2 =
            "<h2><font color=\"#911\">Method not implemented</font> [priority: medium]</h2>\n";
    public static final String methodNotImplemented3 =
            "<h2><font color=\"#911\">Method not implemented</font> [priority: low]</h2>\n";

    @Operation(
            summary = notImplemented,
            description = methodNotImplemented1
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LOW {
    }

    @Operation(
            summary = notImplemented,
            description = methodNotImplemented2
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MEDIUM {
    }

    @Operation(
            summary = notImplemented,
            description = methodNotImplemented1
    )
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface HIGH {
    }

}
