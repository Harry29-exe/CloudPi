package com.cloudpi.cloudpi.file_module.permission.service.dto;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Value
public class CreateFile {

    @NotNull
    VirtualPath path;
    @Nullable
    FileType fileType;

}
