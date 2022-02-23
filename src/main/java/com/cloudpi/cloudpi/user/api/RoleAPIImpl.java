package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.requests.DeleteRoleRequest;
import com.cloudpi.cloudpi.user.api.requests.PostRoleRequest;

import java.util.List;

public class RoleAPIImpl implements RoleAPI {

    @Override
    public List<Role> getUserRoles(String username) {
        return null;
    }

    @Override
    public void addRole(PostRoleRequest request) {

    }

    @Override
    public void removeRole(DeleteRoleRequest request) {

    }
}
