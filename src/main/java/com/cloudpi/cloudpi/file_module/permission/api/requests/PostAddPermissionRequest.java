package com.cloudpi.cloudpi.file_module.permission.api.requests;

import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Value
@Schema(description = "All files are not null")
public class PostAddPermissionRequest {

    @NotNull
    UUID filePubId;

    @NotBlank
    @Schema(description = "Username of user that will receive permission")
    String ownerUsername;

    @NotEmpty
    @Schema(description = "List of permissions that will be given to user")
    List<PermissionType> permissions;

}
