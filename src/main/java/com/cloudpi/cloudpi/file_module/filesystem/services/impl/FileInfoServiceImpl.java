package com.cloudpi.cloudpi.file_module.filesystem.services.impl;

import com.cloudpi.cloudpi.exception.file.NotEnoughSpaceException;
import com.cloudpi.cloudpi.exception.path.PathNotEmptyException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FilesystemRootInfoRepo;
import com.cloudpi.cloudpi.file_module.filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.UpdateVFile;
import com.cloudpi.cloudpi.file_module.physical.domain.DriveRepo;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.UUID;

@AppService
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoRepo fileInfoRepo;
    private final DriveRepo driveRepo;
    private final FilesystemRootInfoRepo filesystemRootInfoRepo;

    public FileInfoServiceImpl(FileInfoRepo fileInfoRepo, DriveRepo driveRepo,
                               FilesystemRootInfoRepo filesystemRootInfoRepo) {
        this.fileInfoRepo = fileInfoRepo;
        this.driveRepo = driveRepo;
        this.filesystemRootInfoRepo = filesystemRootInfoRepo;
    }

    @Override
    public FileInfoDTO save(CreateFileInDB create) {
        if(!canAddNewFile(create.getPath().getUsername(), create.getSize())) {
            throw new NotEnoughSpaceException();
        }

        var parent = fileInfoRepo
                .findByPath(create.getPath().getParentPath())
                .orElseThrow(ResourceNotExistException::new);
        var path = create.getPath();

        var entity = new FileInfo(
                path.getName(),
                parent,
                driveRepo.findByPubId(create.getDriveId())
                        .orElseThrow(),
                create.getType(),
                create.getSize()
        );

        var savedEntity = fileInfoRepo.save(entity);
        return savedEntity.mapToDTO();
    }

    @Override
    public FileInfoDTO saveDir(VirtualPath path) {
        var parent = fileInfoRepo
                .findByPath(path.getParentPath())
                .orElseThrow(ResourceNotExistException::new);

        var entity = FileInfo.createDirectory(
                path.getName(),
                parent
        );
        var savedDir = fileInfoRepo.save(entity);
        return savedDir.mapToDTO();
    }

    @Override
    public FileInfoDTO get(UUID filePubId) {
        return fileInfoRepo
                .findByPubId(filePubId)
                .orElseThrow(ResourceNotExistException::new)
                .mapToDTO();
    }

    @Override
    public void move(UUID filePubId, String newPath) {
        if (!isPathEmpty(newPath)) {
            throw new PathNotEmptyException();
        }

        var file = fileInfoRepo
                .findByPubId(filePubId)
                .orElseThrow(ResourceNotExistException::new);


        if (file.getType() == FileType.DIRECTORY) {
            fileInfoRepo.moveDirectory(newPath, file.getPath());
        } else {
            file.setPath(newPath);
        }
        changeParentAndName(file, newPath);
    }

    @Override
    public FileInfoDTO update(UpdateVFile update) {
        var entity = fileInfoRepo
                .findByPubId(update.getPubId())
                .orElseThrow(ResourceNotExistException::new);

        entity.update(update);

        var updatedEntity = fileInfoRepo.saveAndFlush(entity);
        return updatedEntity.mapToDTO();
    }

    @Override
    public void delete(VirtualPath path) {
        var entity = fileInfoRepo
                .findByPath(path.getPath())
                .orElseThrow(ResourceNotExistException::new);

        fileInfoRepo.delete(entity);
    }

    @Override
    public void delete(UUID fileId) {
        var entity = fileInfoRepo
                .findByPubId(fileId)
                .orElseThrow(ResourceNotExistException::new);

        fileInfoRepo.delete(entity);
    }

    private boolean isPathEmpty(String path) {
        return fileInfoRepo.findByPath(path).isEmpty();
    }

    private void changeParentAndName(FileInfo file, String newPath) {
        VirtualPath newVirtualPath = new VirtualPath(newPath);
        var parent = fileInfoRepo.findByPath(newVirtualPath.getParentPath())
                .orElseThrow(ResourceNotExistException::new);
        file.move(parent);
        file.setName(newVirtualPath.getName());
    }

    private boolean canAddNewFile(String username, long fileSize) {
        var filesystem = filesystemRootInfoRepo.findByOwner_Username(username)
                .orElseThrow(ResourceNotExistException::new);
        var files = fileInfoRepo.findByRootId(filesystem.getId());
        long freeSpace = filesystem.getAssignedCapacity() - files.stream()
                .mapToLong(file -> file.getDetails().getSize()).sum();
        return freeSpace >= fileSize;
    }

}
