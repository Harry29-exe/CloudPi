package com.cloudpi.cloudpi.file_module.physical.services.dto;

import lombok.Value;

@Value
public class CreateDrive {

    String path;
    Long assignedSpace;

}
