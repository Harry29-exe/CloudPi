package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

//@AppService
@Transactional
@Validated
@Service(value = "filePermissionService")
public class FilePermissionServiceImpl implements FilePermissionService {
    private final FilePermissionRepo filePermissionRepo;

    public FilePermissionServiceImpl(FilePermissionRepo filePermissionRepo) {
        this.filePermissionRepo = filePermissionRepo;
    }

    @Override
    public boolean canModify(UUID filePubId) {
        return true;
    }

    @Override
    public boolean canModify(String path) {
        return true;
    }

    @Override
    public boolean canModify(VirtualPath path) {
        return true;
    }

    @Override
    public boolean canRead(UUID filePubId) {
        return true;
    }

    @Override
    public boolean canRead(String path) {
        return true;
    }

    @Override
    public boolean canRead(VirtualPath path) {
        return true;
    }

    protected boolean permissionExist(UUID filePubId,
                                      String username,
                                      PermissionType type) {
        return filePermissionRepo.hasPermission(
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
