package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DriveService {

    DriveDTO getDriveForNewFile(Long fileSize);

}
