package com.cloudpi.cloudpi.file_module.virtual_filesystem.api.dto;

import java.util.UUID;

public record MoveFileRequest(
        UUID filePubId,
        String newPath
) {
}
