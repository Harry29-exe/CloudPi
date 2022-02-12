package com.cloudpi.cloudpi.file_module.virtual_filesystem.services.impl;

import com.cloudpi.cloudpi.exception.file.ChangeDriveSizeException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.service.FilePermissionService;
import com.cloudpi.cloudpi.file_module.physical.services.FileService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FilesystemRootInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FilesystemObjectDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FilesystemRootInfoRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FilesystemInfoService;
import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@AppService
public class FilesystemInfoServiceImp implements FilesystemInfoService {

    private final Long defaultSpaceOnVirtualDrive;
    private final UserRepo userRepository;
    private final FilesystemRootInfoRepo filesystemRootInfoRepo;
    private final FileService fileService;
    private final FileInfoRepo fileInfoRepo;
    private final FilePermissionService filePermissionService;

    public FilesystemInfoServiceImp(
            @Value("${cloud-pi.storage.default-space-on-virtual-drive}")
                    String spaceOnVD,
            UserRepo userRepo,
            FilesystemRootInfoRepo filesystemRootInfoRepo, FileService fileService, FileInfoRepo fileInfoRepo,
            FilePermissionService filePermissionService) {

        this.defaultSpaceOnVirtualDrive =
                Long.parseLong(spaceOnVD.replace("_", ""));
        this.userRepository = userRepo;
        this.filesystemRootInfoRepo = filesystemRootInfoRepo;
        this.fileService = fileService;
        this.fileInfoRepo = fileInfoRepo;
        this.filePermissionService = filePermissionService;
    }

    @Override
    public void createRoot(Long userId, Long driveSize) {
        var user = userRepository.findById(userId)
                .orElseThrow(ResourceNotExistException::new);

        var userDrive = new FilesystemRootInfo(driveSize, user);
        filesystemRootInfoRepo.saveAndFlush(userDrive);


        var rootDir = FileInfo.createRootDir(user.getUsername());
        rootDir.setRoot(userDrive);
        rootDir.setPermissions(grantPermissionsToRoot(user, rootDir));
        fileInfoRepo.saveAndFlush(rootDir);

        userDrive.setRootVDirectory(rootDir);
        filesystemRootInfoRepo.saveAndFlush(userDrive);
    }

    @Override
    public void createRoot(Long userId) {
        this.createRoot(userId, this.defaultSpaceOnVirtualDrive);
    }

    @Override
    public FileStructureDTO get(VirtualPath entryPoint, Integer depth, String username) {
        String path = entryPoint.getPath().isEmpty() ? username : entryPoint.getPath();
        var rootDir = fileInfoRepo.findByPath(path)
                .orElseThrow(ResourceNotExistException::new);

        if (depth <= 0) {
            depth = Integer.MAX_VALUE;
        }
        FilesystemObjectDTO rootObj = rootDir.mapToFilesystemObjectDTO(depth);
        return new FileStructureDTO(entryPoint.getPath(), rootObj);
    }

    @Override
    public FilesystemInfoDTO getUsersVirtualDrives(String username) {
        var filesystem = filesystemRootInfoRepo.findByOwner_Username(username)
                .orElseThrow(ResourceNotExistException::new);
        return getVirtualDrivesInfo(username, filesystem);
    }

    @Override
    public void changeVirtualDriveSize(String username, Long newAssignedSpace) {
        var filesystem = filesystemRootInfoRepo.findByOwner_Username(username)
                .orElseThrow(ResourceNotExistException::new);
        if(getUsedSpace(filesystem) > newAssignedSpace) {
            throw new ChangeDriveSizeException();
        }
        filesystem.setAssignedCapacity(newAssignedSpace);
        filesystemRootInfoRepo.save(filesystem);
    }

    private List<FilePermission> grantPermissionsToRoot(UserEntity user, FileInfo rootDir) {
        List<FilePermission> permissions = new ArrayList<>();
        permissions.add(new FilePermission(PermissionType.READ, user, rootDir));
        permissions.add(new FilePermission(PermissionType.MODIFY, user, rootDir));
        return permissions;
    }

    private FilesystemInfoDTO getVirtualDrivesInfo(String username, FilesystemRootInfo filesystem) {
        long totalSpace = filesystem.getAssignedCapacity();
        long freeSpace = totalSpace - getUsedSpace(filesystem);
        return new FilesystemInfoDTO(username, totalSpace, freeSpace);
    }

    private long getUsedSpace(FilesystemRootInfo filesystem) {
        var files = fileInfoRepo.findByRootId(filesystem.getId());
        return files.stream().mapToLong(file -> file.getDetails().getSize()).sum();
    }
}
