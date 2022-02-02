package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilesystemInfoDTO {

    private String ownerUsername;
    private Long totalSpace;
    private Long freeSpace;

}
