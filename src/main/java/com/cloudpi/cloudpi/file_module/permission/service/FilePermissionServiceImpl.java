package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo;
import com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo.FilePermissionProjection;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.utils.AppService;
import org.hibernate.cfg.NotYetImplementedException;

import java.util.*;
import java.util.stream.Collectors;

@AppService
public class FilePermissionServiceImpl implements FilePermissionService {
    private final FilePermissionRepo filePermissionRepo;
    private final FileInfoRepo fileInfoRepo;

    public FilePermissionServiceImpl(FilePermissionRepo filePermissionRepo, FileInfoRepo fileInfoRepo) {
        this.filePermissionRepo = filePermissionRepo;
        this.fileInfoRepo = fileInfoRepo;
    }


    @Override
    public UserFilePermissionsDTO getUserPermissions(UUID filePubId) {
//        var username = getCurrentUserUsername()
//                .orElseThrow(IllegalStateException::new);
//
//        var permissions = filePermissionRepo.
//                findAllByUser_UsernameAndFile_PubId(username, filePubId);
//
//        return new UserFilePermissionsDTO(
//                permissions, username, filePubId
//        );
        throw new NotYetImplementedException();
    }

    @Override
    public FilePermissionsDTO getPermissionsToFile(UUID filePubId) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<FileInfoDTO> getFilesSharedToUser(String username) {

        return null;
    }

    @Override
    public void grantPermission(PermissionType type, String username, UUID filePubId) {

    }

    @Override
    public void removePermission(PermissionType type, String username, UUID filePubId) {

    }

    private List<FilePermissionsDTO> mapToFilePermissions(List<FilePermissionProjection> permissions) {
        List<FilePermissionsDTO> allPermissions = new ArrayList<>();

        var fileIdPermissionMap = permissions.stream()
                .collect(Collectors.groupingBy(FilePermissionProjection::getFilePubId));

        for (var filePermissions : fileIdPermissionMap.values()) {
            FilePermissionsDTO filePermissionsDTO = new FilePermissionsDTO(
                    filePermissions.get(0).getFilePubId(),
                    new ArrayList<>()
            );
            allPermissions.add(filePermissionsDTO);

            var userPermissionMap = filePermissions
                    .stream()
                    .collect(Collectors.groupingBy(FilePermissionProjection::getUsername));
            for (var userPermissionList : userPermissionMap.values()) {
                var userPermissions = new FilePermissionsDTO.PermissionDTO(
                        userPermissionList.get(0).getUsername(),
                        userPermissionList.stream()
                                .map(FilePermissionProjection::getType)
                                .collect(Collectors.toList())
                );
                filePermissionsDTO.getPermissions().add(userPermissions);
            }
        }

        return allPermissions;
    }

    private List<UserFilePermissionsDTO> mapToUserFilePermissions(List<FilePermissionProjection> permissions) {
        Map<UUID, UserFilePermissionsDTO> fileIdPermissionMap = new TreeMap<>();

        for (var permission : permissions) {
            if (!fileIdPermissionMap.containsKey(permission.getFilePubId())) {
                fileIdPermissionMap.put(
                        permission.getFilePubId(),
                        mapToUserFilePermission(permission)
                );
            } else {
                fileIdPermissionMap.get(permission.getFilePubId())
                        .getTypes()
                        .add(permission.getType());
            }
        }

        return fileIdPermissionMap.values()
                .stream()
                .toList();
    }

    private UserFilePermissionsDTO mapToUserFilePermission(FilePermissionProjection permission) {
        List<PermissionType> array = new ArrayList<>(20);
        array.add(permission.getType());

        return new UserFilePermissionsDTO(
                array,
                permission.getUsername(),
                permission.getFilePubId()
        );
    }

}
