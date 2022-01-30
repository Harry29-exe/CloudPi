package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VFilesystemInfoDTO {

    private String ownerUsername;
    private Long totalSpace;
    private Long freeSpace;

}
