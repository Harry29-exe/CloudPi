package com.cloudpi.cloudpi.file_module.filesystem.services;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.UpdateVFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;


public interface FileInfoService {

    @PreAuthorize("@filePermissionVerifier.canModify(#create.path.parentPath)")
    FileInfoDTO save(CreateFileInDB create);

    @PreAuthorize("@filePermissionVerifier.canModify(#path.parentPath)")
    FileInfoDTO saveDir(VirtualPath path);

    @PreAuthorize("@filePermissionVerifier.canRead(#filePubId)")
    FileInfoDTO get(UUID filePubId);

    @PreAuthorize("@filePermissionVerifier.canRead(#filePath)")
    FileInfoDTO get(String filePath);

    @PreAuthorize("@filePermissionVerifier.canModify(#filePubId)")
    void move(UUID filePubId, String newPath);

    @PreAuthorize("@filePermissionVerifier.canModify(#update.pubId)")
    FileInfoDTO update(UpdateVFile update);

    @PreAuthorize("@filePermissionVerifier.canModify(#path.path)")
    void delete(VirtualPath path);

    @PreAuthorize("@filePermissionVerifier.canModify(#fileId)")
    void delete(UUID fileId);

}
