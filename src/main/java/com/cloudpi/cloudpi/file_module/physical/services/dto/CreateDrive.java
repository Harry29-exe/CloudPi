package com.cloudpi.cloudpi.file_module.physical.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CreateDrive {

    @Schema
    String path;
    Long assignedSpace;

}
