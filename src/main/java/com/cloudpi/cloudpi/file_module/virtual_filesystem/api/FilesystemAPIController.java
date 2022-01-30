package com.cloudpi.cloudpi.file_module.virtual_filesystem.api;

import com.cloudpi.cloudpi_backend.configuration.springdoc.NotImplemented;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.DirectoryDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileDto;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.FileStructureDTO;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.requests.MoveFileRequest;
import com.cloudpi.cloudpi_backend.files.filesystem.dto.responses.GetUserDriveInfo;
import com.cloudpi.cloudpi_backend.files.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi_backend.files.filesystem.services.DirectoryService;
import com.cloudpi.cloudpi_backend.files.filesystem.services.FilesystemService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilesystemAPIController implements FilesystemAPI {
    private final FilesystemService fsInfoService;
    private final DirectoryService dirService;

    public FilesystemAPIController(FilesystemService fsInfoService, DirectoryService dirService) {
        this.fsInfoService = fsInfoService;
        this.dirService = dirService;
    }

    @Override
    public DirectoryDto createDirectory(String directoryPath, Authentication auth) {
        return dirService.createDirectory(new VirtualPath(directoryPath));
    }

    @Override
    public FileStructureDTO getFileStructure(
            Integer structureLevels,
            String fileStructureRoot,
            Authentication auth) {

        return fsInfoService.getFileStructure(
                structureLevels,
                fileStructureRoot.equals("/") ?
                        new VirtualPath(auth.getName()) :
                        new VirtualPath(fileStructureRoot)
        );
    }

    @NotImplemented.HIGH
    @Override
    public FileDto getFileInfo(String fileId, Boolean getWithPermissions) {
        return null;
    }

    @NotImplemented.HIGH
    @Override
    public DirectoryDto getDirInfo(String fileId, Boolean getWithPermissions) {
        return null;
    }


    @NotImplemented.MEDIUM
    @Override
    public void moveFile(MoveFileRequest requestBody) {

    }

    @NotImplemented.MEDIUM
    @Override
    public void deleteDirectory(String directoryId) {

    }

    @NotImplemented.LOW
    @Override
    public List<GetUserDriveInfo> getUsersVirtualDrivesInfo(List<String> usernames) {
        return null;
    }

    @NotImplemented.LOW
    @Override
    public void changeVirtualDriveMaxSize(String username, Long newAssignedSpace) {

    }
}
