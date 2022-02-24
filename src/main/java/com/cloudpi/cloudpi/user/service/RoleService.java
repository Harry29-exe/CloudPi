package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {

    @PreAuthorize("hasAnyRole('"+Role.moderator+"', '"+Role.admin+"') OR "+
            "authentication.name == #username")
    List<Role> getUsersRoles(String username);

    @PreAuthorize("hasRole('"+Role.admin+"')")
    void grant(String recipientUsername, List<Role> roles);

    @PreAuthorize("hasRole('"+Role.admin+"')")
    void revoke(String username, List<Role> roles);

}
