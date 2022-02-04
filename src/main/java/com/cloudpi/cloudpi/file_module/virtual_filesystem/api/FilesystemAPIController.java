package com.cloudpi.cloudpi.file_module.virtual_filesystem.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.api.request.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FilesystemInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilesystemAPIController implements FilesystemAPI {
    private final FilesystemInfoService filesystemInfoService;
    private final FileInfoService fileInfoService;

    public FilesystemAPIController(FilesystemInfoService filesystemInfoService, FileInfoService fileInfoService) {
        this.filesystemInfoService = filesystemInfoService;
        this.fileInfoService = fileInfoService;
    }

    @Override
    public FileStructureDTO getFileStructure(Integer structureLevels, String fileStructureRoot, Authentication auth) {
        return filesystemInfoService.get(new VirtualPath(fileStructureRoot), structureLevels, auth.getName());
    }

    @Override
    public FileInfoDTO createDirectory(String directoryPath, Authentication auth) {
        return null;
    }

    @Override
    public FileInfoDTO getFileInfo(String fileId, Boolean getWithPermissions) {
        return null;
    }

    @Override
    public void moveFile(MoveFileRequest requestBody) {

    }

    @Override
    public void deleteDirectory(String directoryId) {

    }

    @Override
    public List<FilesystemInfoDTO> getUsersVirtualDrivesInfo(String username) {
        return null;
    }

    @Override
    public void changeVirtualDriveMaxSize(String username, Long newAssignedSpace) {

    }
}
