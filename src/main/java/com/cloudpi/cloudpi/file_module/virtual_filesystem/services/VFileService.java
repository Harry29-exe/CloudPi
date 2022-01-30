package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFileDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.CreateFileDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VFileService {

    VFileDTO save(CreateFileDTO fileInfo);

    VFileDTO get(UUID filePubId);

    void move(UUID filePubId, String newPath);

    VFileDTO update();

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(VirtualPath path);

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(UUID fileId);

}
