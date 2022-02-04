package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;


public interface FileInfoService {

    @PreAuthorize("@filePermissionService.canModify(#create.path.parentPath)")
    FileInfoDTO save(CreateFileInDB create);

    @PreAuthorize("@filePermissionService.canModify(#path.parentPath)")
    FileInfoDTO saveDir(VirtualPath path);

    @PreAuthorize("@filePermissionService.canRead(#filePubId)")
    FileInfoDTO get(UUID filePubId);

    @PreAuthorize("@filePermissionService.canModify(#filePubId)")
    void move(UUID filePubId, String newPath);

    @PreAuthorize("@filePermissionService.canModify(#update.pubId)")
    FileInfoDTO update(UpdateVFile update);

    @PreAuthorize("@filePermissionService.canModify(#path.path)")
    void delete(VirtualPath path);

    @PreAuthorize("@filePermissionService.canModify(#fileId)")
    void delete(UUID fileId);

}
