package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotNull;

public interface FilesystemInfoService {

    void createRoot(Long userId, Long driveSize);

    void createRoot(Long userId);

    @PreAuthorize("@filePermissionVerifier.canRead(#entryPoint)")
    FileStructureDTO get(
            VirtualPath entryPoint,
            Integer depth,
            @NotNull String username);

    @PreAuthorize("@filePermissionVerifier.canRead(#username)")
    FilesystemInfoDTO getUsersVirtualDrives(String username);

    @PreAuthorize("@filePermissionVerifier.canModify(#username)")
    void changeVirtualDriveSize(String username, Long newAssignedSpace);

}
