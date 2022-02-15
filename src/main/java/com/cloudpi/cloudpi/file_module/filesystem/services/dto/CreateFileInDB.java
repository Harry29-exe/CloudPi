package com.cloudpi.cloudpi.file_module.filesystem.services.dto;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class CreateFileInDB {
    @NotNull VirtualPath path;
    @NotNull UUID driveId;
    @NotNull FileType type;
    @Min(0) Long size;
}
