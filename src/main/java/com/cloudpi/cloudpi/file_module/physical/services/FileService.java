package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileService {

    @PreAuthorize("@filePermissionService.canModify(#create.path.parentPath)")
    FileInfoDTO create(CreateFile create, MultipartFile file);

    @PreAuthorize("@filePermissionService.canModify(#path.parentPath)")
    FileInfoDTO createDir(VirtualPath path);

    @PreAuthorize("@filePermissionService.canRead(#filePubId)")
    Resource read(UUID filePubId);

    @PreAuthorize("@filePermissionService.canModify(#filePubId)")
    FileInfoDTO modify(UUID filePubId, MultipartFile file);

    @PreAuthorize("@filePermissionService.canModify(#filePubId)")
    void delete(UUID filePubId);

    @PreAuthorize("@filePermissionService.canRead(#imageId)")
    ResponseEntity<byte[]> readPreview(Integer previewResolution, UUID imageId);

}
