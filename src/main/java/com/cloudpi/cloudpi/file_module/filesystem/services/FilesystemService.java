package com.cloudpi.cloudpi.file_module.filesystem.services;

import com.cloudpi.cloudpi.config.security.HasUserRole;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FilesystemService {

    void createRoot(Long userId, Long driveSize);

    void createRoot(Long userId);

    @PreAuthorize("@filePermissionVerifier.canRead(#entryPoint)")
    FileStructureDTO get(
            VirtualPath entryPoint,
            Integer depth,
            @NotNull String username);

    @HasUserRole
    List<FileInfoDTO> getSharedByUser();

    @HasUserRole
    List<FileInfoDTO> getSharedByUser(Pageable pageable);

    @HasUserRole
    List<FileInfoDTO> getSharedToUser();

    @HasUserRole
    List<FileInfoDTO> getSharedToUser(Pageable pageable);

    @PreAuthorize("@filePermissionVerifier.canRead(#username)")
    FilesystemInfoDTO getUsersVirtualDrives(String username);

    @PreAuthorize("@filePermissionVerifier.canModify(#username)")
    void changeVirtualDriveSize(String username, Long newAssignedSpace);

}
