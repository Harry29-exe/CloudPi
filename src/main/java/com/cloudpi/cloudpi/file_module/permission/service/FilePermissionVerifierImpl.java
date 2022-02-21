package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.utils.CurrentRequestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Transactional
@Validated
@Service(value = "filePermissionVerifier")
public class FilePermissionVerifierImpl implements FilePermissionVerifier {
    private final FilePermissionRepo filePermissionRepo;

    public FilePermissionVerifierImpl(FilePermissionRepo filePermissionRepo) {
        this.filePermissionRepo = filePermissionRepo;
    }

    @Override
    public boolean canModify(String path) {
        return filePermissionRepo.hasPermissionByPath(path, retrieveUsername(), PermissionType.MODIFY);
    }

    @Override
    public boolean canModify(VirtualPath path) {
        return canModify(path.getPath());
    }

    @Override
    public boolean canModify(UUID filePubId) {

        return filePermissionRepo.hasPermission(filePubId, retrieveUsername(), PermissionType.MODIFY);
    }

    @Override
    public boolean canRead(UUID filePubId) {
        return filePermissionRepo.hasPermission(filePubId, retrieveUsername(), PermissionType.READ);
    }

    @Override
    public boolean canRead(String path) {
        return filePermissionRepo.hasPermissionByPath(path, retrieveUsername(), PermissionType.READ);
    }

    @Override
    public boolean canRead(VirtualPath path) {
        return canRead(path.getPath());
    }

    @Override
    public boolean canReadAllByPubId(List<UUID> filePubId) {
        return false;
    }

    @Override
    public boolean canReadAllByPath(List<String> path) {
        return false;
    }

    @Override
    public boolean canReadAllByVirtualPath(List<VirtualPath> path) {
        return false;
    }

    private String retrieveUsername() {
        return CurrentRequestUtils.getCurrentUserUsername()
                .orElseThrow(IllegalStateException::new);
    }

}
