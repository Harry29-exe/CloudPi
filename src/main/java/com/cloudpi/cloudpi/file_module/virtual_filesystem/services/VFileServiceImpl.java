package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.exception.path.PathNotEmptyException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.physical.domain.DriveRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateVFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class VFileServiceImpl implements VFileService {
    private final FileInfoRepo fileInfoRepo;
    private final DriveRepo driveRepo;

    public VFileServiceImpl(FileInfoRepo fileInfoRepo, DriveRepo driveRepo) {
        this.fileInfoRepo = fileInfoRepo;
        this.driveRepo = driveRepo;
    }

    @Override
    public FileInfoDTO save(CreateVFile fileInfo) {
        var parent = fileInfoRepo
                .findByPath(fileInfo.getPath().getParentPath())
                .orElseThrow(ResourceNotExistException::new);
        var path = fileInfo.getPath();

        var entity = new FileInfo(
                path.getPath(),
                path.getName(),
                parent,
                driveRepo.getById(fileInfo.getDriveId()),
                fileInfo.getType(),
                fileInfo.getSize()
        );

        var savedEntity = fileInfoRepo.save(entity);
        return savedEntity.mapToDTO();
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
    public void delete(VPath path) {
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
