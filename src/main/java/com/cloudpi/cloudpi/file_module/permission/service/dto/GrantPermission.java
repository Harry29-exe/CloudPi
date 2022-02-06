package com.cloudpi.cloudpi.file_module.permission.service.dto;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class GrantPermission {
    @NotNull PermissionType permissionType;
    @NotNull String username;
    @NotNull UUID fileUUID;
}
