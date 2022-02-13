package com.cloudpi.cloudpi.file_module.permission.dto;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Schema(description = """
        DTO containing all existing permissions to given file.
        """)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilePermissionsDTO {

    private UUID filePubId;
    private List<PermissionDTO> permissions;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PermissionDTO {

        private String ownerUsername;
        private List<PermissionType> permissions;

    }

}
