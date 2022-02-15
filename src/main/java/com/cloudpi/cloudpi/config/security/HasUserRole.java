package com.cloudpi.cloudpi.config.security;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('" + Role.user + "')")
public @interface HasUserRole {
}
