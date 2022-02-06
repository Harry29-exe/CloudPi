package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FilesystemInfoService {

    void createRoot(Long userId, Long driveSize);

    void createRoot(Long userId);

    FileStructureDTO get(
            VirtualPath entryPoint,
            Integer depth,
            @NotNull String username);

    List<FilesystemInfoDTO> getUsersVirtualDrives(String username);

}
