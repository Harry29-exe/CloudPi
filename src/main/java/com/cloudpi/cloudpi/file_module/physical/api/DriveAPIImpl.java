package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.file_module.physical.dto.DiscDTO;
import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import com.cloudpi.cloudpi.file_module.physical.services.DriveService;
import com.cloudpi.cloudpi.file_module.physical.services.dto.CreateDrive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
public class DriveAPIImpl implements DriveAPI {
    private final DriveService driveService;

    public DriveAPIImpl(DriveService driveService) {
        this.driveService = driveService;
    }

    @Override
    public List<DiscDTO> getAllDiscs() {
        return null;
    }

    @Override
    public DriveDTO createNewDrive(PostDriveRequest request) {
        return driveService.add(new CreateDrive(request.path(), request.size()));
    }

    @Override
    public List<DriveDTO> getAllDrives() {
        return null;
    }
}
