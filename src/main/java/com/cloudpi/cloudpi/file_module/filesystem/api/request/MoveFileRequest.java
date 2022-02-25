package com.cloudpi.cloudpi.file_module.filesystem.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record MoveFileRequest(
        @NotNull
        @Schema
        UUID filePubId,
        @NotNull
        @Schema
        String newPath
) {
}
