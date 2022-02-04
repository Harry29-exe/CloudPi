package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.exception.path.PathNotEmptyException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.physical.domain.DriveRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.UUID;

@AppService
public class FileInfoServiceImpl implements FileInfoService {
    private final FileInfoRepo fileInfoRepo;
    private final DriveRepo driveRepo;

    public FileInfoServiceImpl(FileInfoRepo fileInfoRepo, DriveRepo driveRepo) {
        this.fileInfoRepo = fileInfoRepo;
        this.driveRepo = driveRepo;
    }

    @Override
    public FileInfoDTO save(CreateFileInDB create) {
        var parent = fileInfoRepo
                .findByPath(create.getPath().getParentPath())
                .orElseThrow(ResourceNotExistException::new);
        var path = create.getPath();

        var entity = new FileInfo(
                path.getPath(),
                path.getName(),
                parent,
                driveRepo.getById(create.getDriveId()),
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
                path.getPath(),
                parent
        );

        return entity.mapToDTO();
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

}
