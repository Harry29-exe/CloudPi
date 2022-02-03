package com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto;

import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class UpdateVFile {

    @NotNull
    UUID pubId;
    @Nullable
    String newName;


}
