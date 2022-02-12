package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilesystemObjectDTO {

    private UUID pubId;
    private String name;
    private Long size;
    private Date modifiedAt;
    private Integer version;
    private FileType type;
    private List<FilesystemObjectDTO> children;

}
