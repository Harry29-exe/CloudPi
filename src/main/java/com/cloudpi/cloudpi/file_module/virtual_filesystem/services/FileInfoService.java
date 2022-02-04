package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateVFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;

import java.util.UUID;


public interface FileInfoService {

    FileInfoDTO save(CreateVFile fileInfo);

    FileInfoDTO get(UUID filePubId);

    void move(UUID filePubId, String newPath);

    FileInfoDTO update(UpdateVFile update);

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(VirtualPath path);

    //    @PreAuthorize("fileAuthorityVerifier.canWrite(Principal, #path)")
    void delete(UUID fileId);

}
