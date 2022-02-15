package com.cloudpi.cloudpi.file_module.permission.api;

import com.cloudpi.cloudpi.file_module.permission.api.requests.DeleteAllPermissionsRequest;
import com.cloudpi.cloudpi.file_module.permission.api.requests.DeletePermissionsRequest;
import com.cloudpi.cloudpi.file_module.permission.api.requests.PostAddPermissionRequest;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.service.FilePermissionService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.RestController;

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
    public void addPermissions(PostAddPermissionRequest request) {
        filePermissionService.grant(
                request.getOwnerUsername(),
                request.getFilePubId(),
                request.getPermissions()
        );
    }

    @Override
    public void revokePermissions(DeletePermissionsRequest request) {
        filePermissionService.revoke(
                request.getOwnerUsername(),
                request.getFilePubId(),
                request.getPermissions()
        );
    }

    @Override
    public void revokeAllPermissions(DeleteAllPermissionsRequest request) {
        filePermissionService.revokeAll(request.getFilePubId());
    }
}
