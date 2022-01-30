package com.cloudpi.cloudpi.file_module.virtual_filesystem.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.api.request.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFileDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.VFileStructureDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilesystemAPIController implements FilesystemAPI {

    @Override
    public VFileStructureDTO getFileStructure(Integer structureLevels, String fileStructureRoot, Authentication auth) {
        return null;
    }

    @Override
    public VFileDTO createDirectory(String directoryPath, Authentication auth) {
        return null;
    }

    @Override
    public VFileDTO getFileInfo(String fileId, Boolean getWithPermissions) {
        return null;
    }

    @Override
    public void moveFile(MoveFileRequest requestBody) {

    }

    @Override
    public void deleteDirectory(String directoryId) {

    }

    @Override
    public List<VFilesystemInfoDTO> getUsersVirtualDrivesInfo(String username) {
        return null;
    }

    @Override
    public void changeVirtualDriveMaxSize(String username, Long newAssignedSpace) {

    }
}
