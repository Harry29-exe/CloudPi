package com.cloudpi.cloudpi.file_module.permission.repositories;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPermissionView {
    private PermissionType type;
    private String username;

    public UserPermissionView(PermissionType type, String username) {
        this.type = type;
        this.username = username;
    }

}
