package com.cloudpi.cloudpi.file_module.favourite.services;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface FavouriteService {

    @PreAuthorize("@filePermissionVerifier.canRead(#fileId)")
    void changeFavour(UUID fileId);

    List<FileInfoDTO> getFavouriteFiles(String username);
}
