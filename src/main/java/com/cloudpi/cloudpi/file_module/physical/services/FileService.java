package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {

    @PreAuthorize("@filePermissionVerifier.canModify(#create.path.parentPath)")
    FileInfoDTO create(CreateFile create, MultipartFile file);

    @PreAuthorize("@filePermissionVerifier.canModify(#path.parentPath)")
    FileInfoDTO createDir(VirtualPath path);

    @PreAuthorize("@filePermissionVerifier.canRead(#filePubId)")
    Resource read(UUID filePubId);

    @PreAuthorize("@filePermissionVerifier.canModify(#filePubId)")
    FileInfoDTO modify(UUID filePubId, MultipartFile file);

    @PreAuthorize("@filePermissionVerifier.canModify(#filePubId)")
    void delete(UUID filePubId);

    @PreAuthorize("@filePermissionVerifier.canRead(#imageId)")
    ResponseEntity<byte[]> readPreview(Integer previewResolution, UUID imageId);

}
