package com.cloudpi.cloudpi.file_module.favourite.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/favourite/")
@Tag(name = "Favourite API",
        description = "API for adding and retrieving files marked as favourite")
public interface FavouriteAPI {

    @PatchMapping("{fileId}")
    @Operation(summary = "changes file's favour state", description = "Changes file's favour state, which " +
            "means that this method sets file as favourite if it wasn't, and if it was, it stops being favourite " +
            "(changes state for the opposite one)")
    void changeFavour(@PathVariable UUID fileId);

    @GetMapping("all")
    @Operation(summary = "retrieves all files marked as favourite")
    List<FileInfoDTO> getFavouriteFiles(Authentication auth);
}
