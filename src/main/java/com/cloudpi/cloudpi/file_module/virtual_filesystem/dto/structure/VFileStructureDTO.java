package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VFileStructureDTO {

    private String path;
    private VFSObjectDTO root;

}
