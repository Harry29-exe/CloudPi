package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class FilePermissionServiceImpl implements FilePermissionService {
    private final FilePermissionRepo filePermissionRepo;

    public FilePermissionServiceImpl(FilePermissionRepo filePermissionRepo) {
        this.filePermissionRepo = filePermissionRepo;
    }

    @Override
    public boolean canModify(UUID filePubId) {
        return false;
    }

    @Override
    public boolean canModify(String path) {
        return false;
    }

    @Override
    public boolean canModify(VirtualPath path) {
        return false;
    }

    @Override
    public boolean canRead(UUID filePubId) {
        return false;
    }

    @Override
    public boolean canRead(String path) {
        return false;
    }

    @Override
    public boolean canRead(VirtualPath path) {
        return false;
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
