package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.requests.DeleteRoleRequest;
import com.cloudpi.cloudpi.user.api.requests.PostRoleRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/roles/")
public interface RoleAPI {

    @GetMapping("{username}")
    List<Role> getUserRoles(@PathVariable String username);

    @PostMapping
    void addRole(@RequestBody PostRoleRequest request);

    @DeleteMapping
    void removeRole(@RequestBody DeleteRoleRequest request);

}
