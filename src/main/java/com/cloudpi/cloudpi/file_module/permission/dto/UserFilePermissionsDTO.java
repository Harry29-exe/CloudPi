package com.cloudpi.cloudpi.file_module.permission.dto;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Schema(description = """
        DTO containing permissions for particular file that have \
        particular user.
        """)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFilePermissionsDTO {

    private List<PermissionType> types;

    private String username;

    private UUID filePubId;


}
