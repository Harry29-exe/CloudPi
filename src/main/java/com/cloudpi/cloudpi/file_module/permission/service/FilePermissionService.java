package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Transactional
@Validated
public interface FilePermissionService {

    UserFilePermissionsDTO getUserPermissions(UUID filePubId);

    List<UserFilePermissionsDTO> getUserPermissions(List<UUID> filePubIds);

    FilePermissionsDTO getPermissionsToFile(UUID filePubId);

    List<FilePermissionsDTO> getPermissionsToFiles(List<UUID> filePubId);

    void grantPermission(PermissionType type, String username, UUID filePubId);

    void removePermission(PermissionType type, String username, UUID filePubId);


}
