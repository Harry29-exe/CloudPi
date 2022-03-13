package com.cloudpi.cloudpi.file_module.filesystem.dto;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedFileInfoDTO {

    private UUID pubId;
    private String name;
    private String path;
    private Boolean hasThumbnail;
    private FileType type;
    private Long size;
    private Date modifiedAt;
    private Date createdAt;
    private Boolean isFavourite;
    private List<PermissionType> permissions;

    public SharedFileInfoDTO(FileInfoDTO fileInfoDTO, List<PermissionType> permissions) {
        this.pubId = fileInfoDTO.getPubId();
        this.name = fileInfoDTO.getName();
        this.path = fileInfoDTO.getPath();
        this.hasThumbnail = fileInfoDTO.getHasThumbnail();
        this.type = fileInfoDTO.getType();
        this.size = fileInfoDTO.getSize();
        this.modifiedAt = fileInfoDTO.getModifiedAt();
        this.createdAt = fileInfoDTO.getCreatedAt();
        this.isFavourite = fileInfoDTO.getIsFavourite();
        this.permissions = permissions;
    }
}
