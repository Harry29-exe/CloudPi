package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FileInfoDTO {

    private UUID pubId;
    private String name;
    private String path;
    @Nullable
    private UUID parentUUID;
    private Boolean hasThumbnail;
    private FileType type;
    private Long size;
    private Date modifiedAt;
    private Date createdAt;


}
