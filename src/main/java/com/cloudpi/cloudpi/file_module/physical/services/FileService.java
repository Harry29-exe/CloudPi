package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
public interface FileService {

    @PreAuthorize("@filePermissionService.canModify(#create.path)")
    FileInfoDTO save(CreateFile create, MultipartFile file);

    @PreAuthorize("@filePermissionService.canRead(#filePubId)")
    Resource read(UUID filePubId);

    @PreAuthorize("@filePermissionService.canModify(#filePubId)")
    FileInfoDTO modify(UUID filePubId, MultipartFile file);

    @PreAuthorize("@filePermissionService.canModify(#filePubId)")
    void delete(UUID filePubId);


}
