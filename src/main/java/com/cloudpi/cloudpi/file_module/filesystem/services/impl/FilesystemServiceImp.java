package com.cloudpi.cloudpi.file_module.filesystem.services.impl;

import com.cloudpi.cloudpi.exception.file.ChangeDriveSizeException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.filesystem.domain.FilesystemInfo;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.structure.FilesystemObjectDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FilesystemRootInfoRepo;
import com.cloudpi.cloudpi.file_module.filesystem.services.FilesystemService;
import com.cloudpi.cloudpi.user.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cloudpi.cloudpi.utils.CurrentRequestUtils.getCurrentUserUsernameOrThrow;

@AppService
public class FilesystemServiceImp implements FilesystemService {

    private final Long defaultSpaceOnVirtualDrive;
    private final UserRepo userRepository;
    private final FilesystemRootInfoRepo filesystemRootInfoRepo;
    private final FileInfoRepo fileInfoRepo;

    public FilesystemServiceImp(
            @Value("${cloud-pi.storage.default-space-on-virtual-drive}")
                    String spaceOnVD,
            UserRepo userRepo,
            FilesystemRootInfoRepo filesystemRootInfoRepo,
            FileInfoRepo fileInfoRepo
    ) {

        this.defaultSpaceOnVirtualDrive =
                Long.parseLong(spaceOnVD.replace("_", ""));
        this.userRepository = userRepo;
        this.filesystemRootInfoRepo = filesystemRootInfoRepo;
        this.fileInfoRepo = fileInfoRepo;
    }

    @Override
    public void createRoot(Long userId, Long driveSize) {
        var user = userRepository.findById(userId)
                .orElseThrow(ResourceNotExistException::new);

        var userDrive = new FilesystemInfo(driveSize, user);
        filesystemRootInfoRepo.saveAndFlush(userDrive);


        var rootDir = FileInfo.createRootDir(user.getUsername());
        rootDir.setRoot(userDrive);
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
        var files = fileInfoRepo.findAllByFilestructure(
                path, depth
        );

        return transformToFileStructure(files, path);
    }

    @Override
    public List<FileInfoDTO> getSharedByUser() {
        var username = getCurrentUserUsernameOrThrow();
        var sharedFiles = fileInfoRepo.findAllSharedByUser(username);

        return sharedFiles.stream()
                .map(FileInfo::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileInfoDTO> getSharedByUser(Pageable pageable) {
        return null;
    }

    @Override
    public List<FileInfoDTO> getSharedToUser() {
        var username = getCurrentUserUsernameOrThrow();
        var sharedFiles = fileInfoRepo.findAllSharedToUser(username);

        return sharedFiles.stream()
                .map(FileInfo::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileInfoDTO> getSharedToUser(Pageable pageable) {
        return null;
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

    @Override
    public void deleteRoot(String username) {
        filesystemRootInfoRepo.deleteByOwner_Username(username);
    }

    private FilesystemInfoDTO getVirtualDrivesInfo(String username, FilesystemInfo filesystem) {
        long totalSpace = filesystem.getAssignedCapacity();
        long freeSpace = totalSpace - getUsedSpace(filesystem);
        return new FilesystemInfoDTO(username, totalSpace, freeSpace);
    }

    private long getUsedSpace(FilesystemInfo filesystem) {
        var files = fileInfoRepo.findByRootId(filesystem.getId());
        return files.stream().mapToLong(file -> file.getDetails().getSize()).sum();
    }

    private FileStructureDTO transformToFileStructure(List<FileInfo> files, String entryPointPath) {
        Map<Long, List<FileInfo>> parentIdFileInfoMap =
                files.stream()
                        .collect(Collectors.groupingBy(f ->
                                f.getParentId() != null ?
                                        f.getParentId()
                                        : -1
                        ));


        var fsEntryPoint = files.stream()
                .filter(f -> f.getPath().equals(entryPointPath))
                .findFirst()
                .orElseThrow();

        return new FileStructureDTO(
                entryPointPath,
                createFileStructureObject(fsEntryPoint, parentIdFileInfoMap)
        );
    }

    private FilesystemObjectDTO createFileStructureObject(FileInfo entryPoint, Map<Long, List<FileInfo>> parentIdFileMap) {
        var childrenFiles = parentIdFileMap.get(entryPoint.getId());
        if (childrenFiles == null || childrenFiles.isEmpty()) {
            return entryPoint.mapToFilesystemObjectDTO(new ArrayList<>());
        }

        var children = childrenFiles
                .stream()
                .map(child -> createFileStructureObject(child, parentIdFileMap))
                .toList();

        return entryPoint.mapToFilesystemObjectDTO(
                children
        );
    }

}
