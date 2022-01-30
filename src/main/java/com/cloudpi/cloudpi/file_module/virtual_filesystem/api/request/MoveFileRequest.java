package com.cloudpi.cloudpi.file_module.virtual_filesystem.api.request;

import java.util.UUID;

public record MoveFileRequest(
        UUID filePubId,
        String newPath
) {}
