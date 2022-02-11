package com.cloudpi.cloudpi.file_module.sharing.api.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShareFileRequest {
    private UUID filePubId;

}
