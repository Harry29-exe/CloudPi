package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateVFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;

import java.util.UUID;


public interface VFileService {

    FileInfoDTO save(CreateVFile fileInfo);

    FileInfoDTO get(UUID filePubId);

    void move(UUID filePubId, String newPath);

    FileInfoDTO update(UpdateVFile update);

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(VPath path);

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(UUID fileId);

}
