package com.cloudpi.cloudpi.file_module.permission.service.dto;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Value
public class CreateFile {

    @NotNull
    VPath path;
    @Nullable
    FileType fileType;

}
