package com.cloudpi.cloudpi.file_module.permission.api.requests;

import lombok.Value;

import java.util.UUID;

@Value
public class DeleteAllPermissionsRequest {

    UUID filePubId;

}
