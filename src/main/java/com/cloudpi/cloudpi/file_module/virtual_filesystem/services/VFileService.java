package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFileDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateVFileDTO;

import java.util.UUID;


public interface VFileService {

    VFileDTO save(CreateVFileDTO fileInfo);

    VFileDTO get(UUID filePubId);

    void move(UUID filePubId, String newPath);

    VFileDTO update();

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(VPath path);

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(UUID fileId);

}
