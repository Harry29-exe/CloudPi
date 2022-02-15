package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

//todo add PreAuthorize
@Transactional
@Validated
public interface FilePermissionService {

    UserFilePermissionsDTO get(UUID filePubId);

    FilePermissionsDTO getPermissionsToFile(UUID filePubId);

    List<FileInfoDTO> getFilesSharedToUser(String username);

    void grant(String username, UUID filePubId, List<PermissionType> types);

    void revoke(String username, UUID filePubId, List<PermissionType> types);

    void revokeAll(UUID filePubId);


}
