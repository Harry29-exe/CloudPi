package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.requests.DeleteRoleRequest;
import com.cloudpi.cloudpi.user.api.requests.PostRoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequestMapping("/roles/")
public interface RoleAPI {

    @PreAuthorize("hasAnyRole('" + Role.admin + "', '" + Role.moderator + "') OR " +
            "authentication.name == #username")
    @GetMapping("{username}")
    @Operation(summary = "Get all user's roles",
            description = "Get all users roles, to access endpoint user need mode or admin role " +
                    "or user's username and username in request need to match")
    List<Role> getUserRoles(@PathVariable String username);

    @PreAuthorize("hasRole('" + Role.admin + "')")
    @PostMapping
    @Operation(summary = "Gives user roles specified in request body",
            description = "Gives user roles specified in request body, admin role is needed to access")
    void addRole(@RequestBody PostRoleRequest request);

    @DeleteMapping
    @Operation(summary = "Remove user's roles specified in request body",
            description = "Remove user's roles specified in request body, admin role is needed to access")
    void removeRole(@RequestBody DeleteRoleRequest request);

}
