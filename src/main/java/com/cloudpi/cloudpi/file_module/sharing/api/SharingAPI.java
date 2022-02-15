package com.cloudpi.cloudpi.file_module.sharing.api;

import com.cloudpi.cloudpi.file_module.sharing.api.requests.ShareFileRequest;
import com.cloudpi.cloudpi.file_module.sharing.dto.UserSharedFilesDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/sharing/")
@Tag(name = "Sharing API",
        description = "API for sharing and retrieving files shared by other users")
public interface SharingAPI {

    @GetMapping("browse")
    List<UserSharedFilesDTO> getSharedFiles(Authentication auth);

    @PostMapping("add")
    void shareFile(@RequestBody ShareFileRequest request, Authentication auth);
}
