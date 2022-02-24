package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.requests.DeleteRoleRequest;
import com.cloudpi.cloudpi.user.api.requests.PostRoleRequest;
import com.cloudpi.cloudpi.user.service.RoleService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleAPIImpl implements RoleAPI {
    private final RoleService roleService;

    public RoleAPIImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<Role> getUserRoles(String username) {
        return roleService.getUsersRoles(username);
    }

    @Override
    public void addRole(PostRoleRequest request) {
        roleService.grant(request.username(), request.roles());
    }

    @Override
    public void removeRole(DeleteRoleRequest request) {
        roleService.revoke(request.username(), request.roles());
    }
}
