package com.cloudpi.cloudpi.file_module.sharing.services;

import com.cloudpi.cloudpi.file_module.sharing.api.requests.ShareFileRequest;
import com.cloudpi.cloudpi.file_module.sharing.dto.UserSharedFilesDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SharingService {
    List<UserSharedFilesDTO> getSharedFiles(String username);

    @PreAuthorize("@filePermissionVerifier.canModify(#shareFileRequest.filePubId)")
    void shareFiles(ShareFileRequest shareFileRequest, String owner);
}
