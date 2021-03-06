package com.cloudpi.cloudpi.file_module.filesystem.dto.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileStructureDTO {

    private String path;
    private FilesystemObjectDTO root;

}
