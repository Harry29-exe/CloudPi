package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class FilePermissionServiceImpl implements FilePermissionService {
    private final FilePermissionRepo filePermissionRepo;

    public FilePermissionServiceImpl(FilePermissionRepo filePermissionRepo) {
        this.filePermissionRepo = filePermissionRepo;
    }

    @Override
    public boolean canModify(UUID filePubId) {
        return canModify(filePubId, getCurrentUserUsername());
    }

    @Override
    public boolean canModify(UUID filePubId, String username) {
        return permissionExist(filePubId, username, PermissionType.MODIFY);
    }

    @Override
    public boolean canRead(UUID filePubId) {
        return canRead(filePubId, getCurrentUserUsername());
    }

    @Override
    public boolean canRead(UUID filePubId, String username) {
        return permissionExist(filePubId, username, PermissionType.READ);
    }

    protected boolean permissionExist(UUID filePubId,
                                      String username,
                                      PermissionType type) {
        return filePermissionRepo.permissionExists(
                filePubId,
                username,
                type
        );
    }

    private String getCurrentUserUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

}
