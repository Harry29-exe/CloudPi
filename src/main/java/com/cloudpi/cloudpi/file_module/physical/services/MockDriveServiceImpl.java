package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import com.cloudpi.cloudpi.file_module.physical.services.dto.CreateDrive;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.UUID;

//@AppService
public class MockDriveServiceImpl implements DriveService {
    private final String pathToSaveFiles;

    public MockDriveServiceImpl(@Value("app.save-files-dir") String pathToSaveFiles) {
        this.pathToSaveFiles = pathToSaveFiles;
    }

    @PostConstruct
    public void addMockDrive() {

    }

    @Override
    public DriveDTO getDriveForNewFile(Long fileSize) {
        UUID uuid = new UUID(0L, 0L);
        return new DriveDTO(
                uuid,
                pathToSaveFiles,
                (long) Math.pow(10, 9),
                (long) Math.pow(10, 9)
        );
    }

    @Override
    public DriveDTO add(CreateDrive createDrive) {
        return null;
    }

    @Override
    public Path getPath(UUID drivePubId) {
        return Path.of(pathToSaveFiles);
    }

    @Override
    public Path getPathToFile(UUID filePubId) {

        return Path.of(pathToSaveFiles + filePubId);
    }
}
