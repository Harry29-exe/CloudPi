package com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateVFile {
    VPath path;
    UUID driveId;
    FileType type;
    Long size;
}
