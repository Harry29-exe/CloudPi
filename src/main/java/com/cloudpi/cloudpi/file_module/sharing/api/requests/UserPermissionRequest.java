package com.cloudpi.cloudpi.file_module.sharing.api.requests;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserPermissionRequest {
    private @NotNull String username;
    private PermissionType type;
}
