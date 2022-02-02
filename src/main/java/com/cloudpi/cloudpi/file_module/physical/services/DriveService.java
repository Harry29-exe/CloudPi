package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import com.cloudpi.cloudpi.file_module.physical.services.dto.CreateDrive;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public interface DriveService {

    DriveDTO getDriveForNewFile(Long fileSize);

    DriveDTO add(CreateDrive createDrive);

    DriveDTO moveFilesAndDelete(UUID drivePubId);

}
