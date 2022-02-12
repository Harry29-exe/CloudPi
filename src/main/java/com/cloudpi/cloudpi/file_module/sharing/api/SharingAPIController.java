package com.cloudpi.cloudpi.file_module.sharing.api;

import com.cloudpi.cloudpi.file_module.sharing.api.requests.ShareFileRequest;
import com.cloudpi.cloudpi.file_module.sharing.dto.UserSharedFilesDTO;
import com.cloudpi.cloudpi.file_module.sharing.services.SharingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SharingAPIController implements SharingAPI {
    private final SharingService sharingService;

    public SharingAPIController(SharingService sharingService) {
        this.sharingService = sharingService;
    }

    @Override
    public List<UserSharedFilesDTO> getSharedFiles(Authentication auth) {
        return sharingService.getSharedFiles(auth.getName());
    }

    @Override
    public void shareFile(ShareFileRequest request, Authentication auth) {
        sharingService.shareFiles(request, auth.getName());
    }
}
