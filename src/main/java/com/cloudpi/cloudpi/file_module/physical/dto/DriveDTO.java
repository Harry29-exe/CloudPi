package com.cloudpi.cloudpi.file_module.physical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class DriveDTO {

    private UUID pubId;
    private String path;
    private Long assignedSpace;
    private Long freeSpace;

}
