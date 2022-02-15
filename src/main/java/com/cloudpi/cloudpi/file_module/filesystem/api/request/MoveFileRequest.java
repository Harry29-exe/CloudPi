package com.cloudpi.cloudpi.file_module.filesystem.api.request;

import java.util.UUID;

public record MoveFileRequest(
        UUID filePubId,
        String newPath
) {
}
