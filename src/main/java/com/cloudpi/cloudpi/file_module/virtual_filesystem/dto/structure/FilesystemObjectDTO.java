package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FilesystemObjectDTO {

    private UUID pubId;
    private String name;
    private Integer version;
    private List<FilesystemObjectDTO> children;

}
