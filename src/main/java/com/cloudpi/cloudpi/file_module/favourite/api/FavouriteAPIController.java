package com.cloudpi.cloudpi.file_module.favourite.api;

import com.cloudpi.cloudpi.file_module.favourite.services.FavouriteService;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class FavouriteAPIController implements FavouriteAPI {
    private final FavouriteService favouriteService;

    public FavouriteAPIController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @Override
    public void changeFavour(UUID fileId) {
        favouriteService.changeFavour(fileId);
    }

    @Override
    public List<FileInfoDTO> getFavouriteFiles(Authentication auth) {
        return favouriteService.getFavouriteFiles(auth.getName());
    }
}
