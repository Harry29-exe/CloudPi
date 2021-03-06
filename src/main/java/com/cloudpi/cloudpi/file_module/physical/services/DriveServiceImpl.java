package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.exception.drive.IncorrectPathException;
import com.cloudpi.cloudpi.exception.drive.NoDrivesException;
import com.cloudpi.cloudpi.exception.file.NotEnoughSpaceException;
import com.cloudpi.cloudpi.exception.path.IncorrectPathingException;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.physical.domain.Drive;
import com.cloudpi.cloudpi.file_module.physical.domain.DriveRepo;
import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import com.cloudpi.cloudpi.file_module.physical.services.dto.CreateDrive;
import com.cloudpi.cloudpi.utils.AppService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@AppService
public class DriveServiceImpl implements DriveService {
    private final DriveRepo driveRepo;

    public DriveServiceImpl(DriveRepo driveRepo) {
        this.driveRepo = driveRepo;
    }

    @Override
    public DriveDTO getDriveForNewFile(Long fileSize) {
        var drives = driveRepo.findAll();
        if (drives.isEmpty()) {
            throw new NoDrivesException();
        }

        var drivesWithEnoughSpace = drives.stream()
                .filter(drive -> drive.getFreeSpace() >= fileSize)
                .collect(Collectors.toList());

        if (drivesWithEnoughSpace.isEmpty()) {
            throw new NotEnoughSpaceException();
        }

        drivesWithEnoughSpace
                .sort(Comparator.comparingDouble(Drive::calcFillPerc));

        return drivesWithEnoughSpace.get(0).mapToDTO();
    }

    @Override
    public DriveDTO add(CreateDrive createDrive) {
        var path = Paths.get(createDrive.getPath());
        if (!path.toFile().exists()) {
            throw new IncorrectPathException();
        }

        var drive = new Drive(
                createDrive.getPath(),
                createDrive.getAssignedSpace()
        );
        driveRepo.save(drive);

        return drive.mapToDTO();
    }

    @Override
    public Path getPath(UUID drivePubId) {
        var drive = driveRepo.findByPubId(drivePubId)
                .orElseThrow(ResourceNotExistException::new);
        return Paths.get(drive.getPath());
    }

    @Override
    public Path getPathToFile(UUID filePubId) {
        var drive = driveRepo.findByAssignedFile(filePubId)
                .orElseThrow(ResourceNotExistException::new);

        return Paths.get(drive.getPath() + "/" + filePubId);
    }
}
