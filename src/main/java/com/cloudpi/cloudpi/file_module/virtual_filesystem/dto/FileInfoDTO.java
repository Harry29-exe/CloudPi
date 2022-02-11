package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Boolean isFavourite;

}
