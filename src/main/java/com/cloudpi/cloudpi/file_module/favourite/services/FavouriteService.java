package com.cloudpi.cloudpi.file_module.favourite.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

public interface FavouriteService {

    @PreAuthorize("@filePermissionService.canRead(#fileId)")
    void changeFavour(UUID fileId);

    List<FileInfoDTO> getFavouriteFiles(String username);
}
