package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.exception.path.PathNotEmptyException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.physical.domain.DriveRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.VFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.repositories.VFileRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFileDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateVFileDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class VFileServiceImpl implements VFileService {
    private final VFileRepo vFileRepo;
    private final DriveRepo driveRepo;

    public VFileServiceImpl(VFileRepo vFileRepo, DriveRepo driveRepo) {
        this.vFileRepo = vFileRepo;
        this.driveRepo = driveRepo;
    }

    @Override
    public VFileDTO save(CreateVFileDTO fileInfo) {
        var parent = vFileRepo
                .findByPath(fileInfo.getPath().getParentPath())
                .orElseThrow(ResourceNotExistException::new);
        var path = fileInfo.getPath();

        var entity = new VFile(
                path.getPath(),
                path.getName(),
                parent,
                driveRepo.getById(fileInfo.getDriveId()),
                fileInfo.getType(),
                fileInfo.getSize()
        );

        var savedEntity = vFileRepo.save(entity);
        return savedEntity.mapToDTO();
    }

    @Override
    public VFileDTO get(UUID filePubId) {
        return vFileRepo
                .findByPubId(filePubId)
                .orElseThrow(ResourceNotExistException::new)
                .mapToDTO();
    }

    @Override
    public void move(UUID filePubId, String newPath) {
        if(!isPathEmpty(newPath)) {
            throw new PathNotEmptyException();
        }

        var file = vFileRepo
                .findByPubId(filePubId)
                .orElseThrow(ResourceNotExistException::new);


        if(file.getType() == FileType.DIRECTORY) {
            vFileRepo.moveDirectory(newPath, file.getPath());
        } else {
            file.setPath(newPath);
        }
    }

    @Override
    public VFileDTO update() {
        return null;
    }

    @Override
    public void delete(VPath path) {

    }

    @Override
    public void delete(UUID fileId) {

    }

    private boolean isPathEmpty(String path) {
        return vFileRepo.findByPath(path).isEmpty();
    }

}
