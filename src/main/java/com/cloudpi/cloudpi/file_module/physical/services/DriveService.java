package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import com.cloudpi.cloudpi.file_module.physical.services.dto.CreateDrive;

import java.nio.file.Path;
import java.util.UUID;

public interface DriveService {

    DriveDTO getDriveForNewFile(Long fileSize);

    DriveDTO add(CreateDrive createDrive);

    Path getPath(UUID drivePubId);

    Path getPathToFile(UUID filePubId);

//    DriveDTO moveFilesAndDelete(UUID drivePubId);

}
