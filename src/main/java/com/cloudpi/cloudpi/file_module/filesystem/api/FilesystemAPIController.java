package com.cloudpi.cloudpi.file_module.filesystem.api;

import com.cloudpi.cloudpi.file_module.filesystem.api.request.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.filesystem.services.FileSearchService;
import com.cloudpi.cloudpi.file_module.filesystem.services.FilesystemService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FilesystemAPIController implements FilesystemAPI {
    private final FilesystemService filesystemService;
    private final FileInfoService fileInfoService;
    private final FileSearchService fileSearchService;

    public FilesystemAPIController(FilesystemService filesystemService, FileInfoService fileInfoService, FileSearchService fileSearchService) {
        this.filesystemService = filesystemService;
        this.fileInfoService = fileInfoService;
        this.fileSearchService = fileSearchService;
    }

    @Override
    public FileStructureDTO getFileStructure(Integer structureLevels, String fileStructureRoot, Authentication auth) {
        return filesystemService.get(new VirtualPath(fileStructureRoot), structureLevels, auth.getName());
    }

    @Override
    public List<FileInfoDTO> getFilesSharedByUser() {
        return filesystemService.getSharedByUser();
    }

    @Override
    public List<FileInfoDTO> getFilesSharedToUser() {
        return filesystemService.getSharedToUser();
    }

    @Override
    public FileInfoDTO createDirectory(String directoryPath, Authentication auth) {
        return fileInfoService.saveDir(new VirtualPath(directoryPath));
    }

    @Override
    public FileInfoDTO getFileInfo(UUID fileId, Boolean getWithPermissions) {
        return fileInfoService.get(fileId);
    }

    @Override
    public FileInfoDTO getFileInfoByPath(String filePath, Boolean getWithPermissions) {
        return fileInfoService.get(filePath);
    }

    @Override
    public void moveFile(MoveFileRequest requestBody) {
        fileInfoService.move(requestBody.filePubId(), requestBody.newPath());
    }

    @Override
    public void deleteDirectory(String directoryId) {
        fileInfoService.delete(UUID.fromString(directoryId));
    }

    @Override
    public FilesystemInfoDTO getUsersFilesystemInfo(String username) {
        return filesystemService.getUsersVirtualDrives(username);
    }

    @Override
    public void changeUserFilesystemMaxSize(String username, Long newAssignedSpace) {
        filesystemService.changeVirtualDriveSize(username, newAssignedSpace);
    }

    @Override
    public List<FileInfoDTO> searchInUserFiles(FileQueryDTO searchQuery, Authentication auth) {
        return fileSearchService.find(searchQuery);
    }
}
