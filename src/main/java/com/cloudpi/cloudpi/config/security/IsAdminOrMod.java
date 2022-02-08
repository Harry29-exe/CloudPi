package com.cloudpi.cloudpi.config.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@PreAuthorize("hasAnyRole('"+Role.admin+"', '"+Role.moderator+"')")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IsAdminOrMod {
}
