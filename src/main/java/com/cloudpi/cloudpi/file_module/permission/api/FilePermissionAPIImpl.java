package com.cloudpi.cloudpi.file_module.permission.api;

import com.cloudpi.cloudpi.file_module.permission.api.requests.PostAddPermissionRequest;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.service.FilePermissionVerifier;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FilePermissionAPIImpl implements FilePermissionAPI {
    private final FilePermissionVerifier filePermissionVerifier;

    public FilePermissionAPIImpl(FilePermissionVerifier filePermissionVerifier) {
        this.filePermissionVerifier = filePermissionVerifier;
    }

    @Override
    public UserFilePermissionsDTO getUsersPermissions(UUID filePubId) {
        return null;
    }

    @Override
    public List<UserFilePermissionsDTO> getUsersPermissions(List<UUID> filePubIds) {
        return null;
    }

    @Override
    public FilePermissionsDTO getFilePermissions(String filePubId) {
        return null;
    }

    @Override
    public List<FilePermissionsDTO> getFilesPermissions(String filePubId) {
        return null;
    }

    @Override
    public List<FileInfoDTO> getFilesSharedByUser() {
        return null;
    }

    @Override
    public List<FileInfoDTO> getFilesSharedToUser() {
        return null;
    }

    @Override
    public void addPermissions(PostAddPermissionRequest request) {

    }

    @Override
    public void revokePermissions(DeletePermissionsRequest request) {

    }
}
