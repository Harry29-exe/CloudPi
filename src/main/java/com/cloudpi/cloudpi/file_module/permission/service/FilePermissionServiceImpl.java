package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.file_module.permission.repositories.UserPermissionView;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.AppService;
import com.cloudpi.cloudpi.utils.CurrentRequestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.*;

@AppService
public class FilePermissionServiceImpl implements FilePermissionService {
    private final FilePermissionRepo filePermissionRepo;
    private final FileInfoRepo fileInfoRepo;
    private final UserRepo userRepo;

    public FilePermissionServiceImpl(FilePermissionRepo filePermissionRepo, FileInfoRepo fileInfoRepo, UserRepo userRepo) {
        this.filePermissionRepo = filePermissionRepo;
        this.fileInfoRepo = fileInfoRepo;
        this.userRepo = userRepo;
    }


    @Override
    public UserFilePermissionsDTO get(UUID filePubId) {
        var username = CurrentRequestUtils.getCurrentUserUsername()
                .orElseThrow(IllegalStateException::new);

        var permissions = filePermissionRepo
                .findAllUserFilePermissions(username, filePubId);

        return new UserFilePermissionsDTO(
                permissions,
                username,
                filePubId
        );
    }

    @Override
    public FilePermissionsDTO getPermissionsToFile(UUID filePubId) {
        var permissionsDTO = new ArrayList<FilePermissionsDTO.PermissionDTO>();

        filePermissionRepo
                .findAllFilePermissions(filePubId)
                .stream()
                .collect(
                        groupingBy(
                                UserPermissionView::getUsername,
                                mapping(UserPermissionView::getType, toList())
                        )
                ).forEach((username, typeList) ->
                        permissionsDTO.add(new FilePermissionsDTO.PermissionDTO(username, typeList))
                );

        return new FilePermissionsDTO(
                filePubId,
                permissionsDTO
        );
    }

    @Override
    public List<FileInfoDTO> getFilesSharedToUser(String username) {

        return null;
    }

    @Override
    public void grant(String username, UUID filePubId, List<PermissionType> types) {
        var file = fileInfoRepo.findByPubId(filePubId)
                .orElseThrow(ResourceNotExistException::new);
        var user = userRepo.findByUsername(username)
                .orElseThrow(ResourceNotExistException::new);

        var newPermissions = new ArrayList<FilePermission>();
        for (var type : types) {
            newPermissions.add(
                    new FilePermission(type, user, file)
            );
        }
        filePermissionRepo.saveAllAndFlush(newPermissions);
    }

    @Override
    public void revoke(String username, UUID filePubId, List<PermissionType> types) {
//        var permissionToRemove = filePermissionRepo.findAll()
        filePermissionRepo.removeByUser_UsernameAndFile_PubIdAndTypeIn(
                username,
                filePubId,
                types
        );
    }

    @Override
    public void revokeAll(UUID filePubId) {
        filePermissionRepo.removeByFile_PubId(filePubId);
    }
}
