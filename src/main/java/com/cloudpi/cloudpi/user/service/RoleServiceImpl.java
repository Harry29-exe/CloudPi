package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.exception.user.UserNotExistException;
import com.cloudpi.cloudpi.user.domain.RoleEntity;
import com.cloudpi.cloudpi.user.repositiories.RoleRepo;
import com.cloudpi.cloudpi.user.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AppService
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public RoleServiceImpl(RoleRepo roleRepo, UserRepo userRepo) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Role> getUsersRoles(String username) {
        return roleRepo.findAllByUser_Username(username)
                .stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void grant(String recipientUsername, List<Role> roles) {
        var user = userRepo
                .findByUsername(recipientUsername)
                .orElseThrow(ResourceNotExistException::new);

        for(var role : roles) {
            user.addRole(role);
        }
    }

    @Override
    public void revoke(String username, List<Role> roles) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);

        roles.forEach(user::removeRole);
    }
}
