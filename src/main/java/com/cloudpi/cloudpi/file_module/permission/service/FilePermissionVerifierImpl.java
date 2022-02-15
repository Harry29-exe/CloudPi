package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.CurrentRequestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Transactional
@Validated
@Service(value = "filePermissionVerifier")
public class FilePermissionVerifierImpl implements FilePermissionVerifier {
    private final FilePermissionRepo filePermissionRepo;
    private final FileInfoRepo fileInfoRepo;
    private final UserRepo userRepo;

    public FilePermissionVerifierImpl(FilePermissionRepo filePermissionRepo, FileInfoRepo fileInfoRepo,
                                      UserRepo userRepo) {
        this.filePermissionRepo = filePermissionRepo;
        this.fileInfoRepo = fileInfoRepo;
        this.userRepo = userRepo;
    }

    @Override
    public boolean canModify(String path) {
        return filePermissionRepo.hasPermission(path, retrieveUsername(), PermissionType.MODIFY);
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
        return filePermissionRepo.hasPermission(path, retrieveUsername(), PermissionType.READ);
    }

    @Override
    public boolean canRead(VirtualPath path) {
        return canRead(path.getPath());
    }

//    @Override
//    public void grantPermissions(Set<GrantPermission> permissions) {
//        Set<FilePermission> filePers = new HashSet<>();
//        permissions.forEach(per -> {
//            if(permissionExist(per.getFileUUID(), per.getUsername(), per.getPermissionType())) {
//                throw new PermissionAlreadyExistsException();
//            }
//
//            var user = userRepo.findByUsername(per.getUsername())
//                    .orElseThrow(ResourceNotExistException::new);
//            var file = fileInfoRepo.findByPubId(per.getFileUUID())
//                    .orElseThrow(ResourceNotExistException::new);
//            filePers.add(new FilePermission(per.getPermissionType(), user, file));
//        });
//        filePermissionRepo.saveAll(filePers);
//    }
//
//    @Override
//    public void revokePermissions(Set<RevokePermission> permissions) {
//        Set<FilePermission> filePers = new HashSet<>();
//        permissions.forEach(per -> {
//            if(!permissionExist(per.getFileUUID(), per.getUsername(), per.getPermissionType())) {
//                throw new NoSuchPermissionException();
//            }
//
//            var user = userRepo.findByUsername(per.getUsername())
//                    .orElseThrow(ResourceNotExistException::new);
//            var file = fileInfoRepo.findByPubId(per.getFileUUID())
//                    .orElseThrow(ResourceNotExistException::new);
//            filePers.add(new FilePermission(per.getPermissionType(), user, file));
//        });
//        filePermissionRepo.deleteAll(filePers);
//    }

    protected boolean permissionExist(UUID filePubId,
                                      String username,
                                      PermissionType type) {
        return filePermissionRepo.hasPermission(
                filePubId,
                username,
                type
        );
    }

    private boolean readAllowed(FileInfo file, String username) {
        return file.getPermissions()
                .stream()
                .anyMatch(per -> per.getUser().getUsername().equals(username)
                        && (per.getType() == PermissionType.MODIFY || per.getType() == PermissionType.READ));
    }

    private boolean modifyAllowed(FileInfo file, String username) {
        return file.getPermissions()
                .stream()
                .anyMatch(per -> per.getUser().getUsername().equals(username)
                        && per.getType() == PermissionType.MODIFY);
    }

    private String retrieveUsername() {
        return CurrentRequestUtils.getCurrentUserUsername()
                .orElseThrow(IllegalStateException::new);
    }

}
