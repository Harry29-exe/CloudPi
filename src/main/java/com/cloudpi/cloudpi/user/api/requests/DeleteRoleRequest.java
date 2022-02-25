package com.cloudpi.cloudpi.user.api.requests;

import com.cloudpi.cloudpi.config.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record DeleteRoleRequest(
        @Schema(description = "Must contain at least one role")
        @NotBlank List<Role> roles,
        String username
) {
}
