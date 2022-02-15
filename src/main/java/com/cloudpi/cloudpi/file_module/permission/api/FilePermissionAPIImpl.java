package com.cloudpi.cloudpi.file_module.permission.api;

import com.cloudpi.cloudpi.file_module.permission.api.requests.DeleteAllPermissionsRequest;
import com.cloudpi.cloudpi.file_module.permission.api.requests.PostAddPermissionRequest;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.service.FilePermissionService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FilePermissionAPIImpl implements FilePermissionAPI {
    private final FilePermissionService filePermissionService;

    public FilePermissionAPIImpl(FilePermissionService filePermissionService) {
        this.filePermissionService = filePermissionService;
    }

    @Override
    public UserFilePermissionsDTO getUsersPermissions(UUID filePubId) {
        throw new NotYetImplementedException();
    }

    @Override
    public FilePermissionsDTO getFilePermissions(String filePubId) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<FileInfoDTO> getFilesSharedByUser() {
        throw new NotYetImplementedException();
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

    @Override
    public void revokeAllPermissions(DeleteAllPermissionsRequest request) {

    }
}
