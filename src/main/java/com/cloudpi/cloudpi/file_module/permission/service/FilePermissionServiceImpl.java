package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

// TODO sprawdzic czy dziala, poprawic wyglad, wyjatki etc
@AppService
public class FilePermissionServiceImpl implements FilePermissionService {
    private final FilePermissionRepo filePermissionRepo;
    private final FileInfoRepo fileInfoRepo;

    public FilePermissionServiceImpl(FilePermissionRepo filePermissionRepo, FileInfoRepo fileInfoRepo) {
        this.filePermissionRepo = filePermissionRepo;
        this.fileInfoRepo = fileInfoRepo;
    }

    @Override
    public boolean canModify(UUID filePubId) {
        FileInfo file = fileInfoRepo.findByPubId(filePubId)
                .orElseThrow();
        String username = getCurrentUserUsername();

        while(file != null) {
            boolean canModify = checkPermission(file, PermissionType.MODIFY, username);
            if(canModify) {
                return true;
            }
            file = file.getParent();
        }
        return false;
    }

    @Override
    public boolean canModify(String path) {
        FileInfo file = fileInfoRepo.findByPath(path)
                .orElseThrow();
        String username = getCurrentUserUsername();

        while(file != null) {
            boolean canModify = checkPermission(file, PermissionType.MODIFY, username);
            if(canModify) {
                return true;
            }
            file = file.getParent();
        }
        return false;
    }

    @Override
    public boolean canModify(VirtualPath path) {
        return canModify(path.getPath());
    }

    @Override
    public boolean canRead(UUID filePubId) {
        FileInfo file = fileInfoRepo.findByPubId(filePubId)
                .orElseThrow();
        String username = getCurrentUserUsername();

        while(file != null) {
            boolean canModify = checkPermission(file, PermissionType.READ, username);
            if(canModify) {
                return true;
            }
            file = file.getParent();
        }
        return false;
    }

    @Override
    public boolean canRead(String path) {
        FileInfo file = fileInfoRepo.findByPath(path)
                .orElseThrow();
        String username = getCurrentUserUsername();

        while(file != null) {
            boolean canModify = checkPermission(file, PermissionType.READ, username);
            if(canModify) {
                return true;
            }
            file = file.getParent();
        }
        return false;
    }

    @Override
    public boolean canRead(VirtualPath path) {
        return canRead(path.getPath());
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

    private boolean checkPermission(FileInfo file, PermissionType permission, String username) {
        return file.getPermissions()
                .stream()
                .anyMatch(per -> per.getType() == permission
                        && per.getUser().getUsername().equals(username)
                );
    }

}
