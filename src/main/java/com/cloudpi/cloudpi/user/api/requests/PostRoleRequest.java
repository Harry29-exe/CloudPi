package com.cloudpi.cloudpi.user.api.requests;

import com.cloudpi.cloudpi.config.security.Role;

import java.util.List;

public record PostRoleRequest(
        List<Role> roles,
        String username
) {
}
