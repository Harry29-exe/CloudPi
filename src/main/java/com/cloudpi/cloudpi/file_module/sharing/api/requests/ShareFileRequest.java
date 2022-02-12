package com.cloudpi.cloudpi.file_module.sharing.api.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
public class ShareFileRequest {
    private UUID filePubId;
    private List<UserPermissionRequest> userPermissions;
}
