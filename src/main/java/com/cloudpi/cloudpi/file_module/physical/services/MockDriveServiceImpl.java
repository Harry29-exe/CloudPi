package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class MockDriveServiceImpl implements DriveService {
    private final String pathToSaveFiles;

    public MockDriveServiceImpl(@Value("app.save-files-dir") String pathToSaveFiles) {
        this.pathToSaveFiles = pathToSaveFiles;
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

}
