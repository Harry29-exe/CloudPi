package com.cloudpi.cloudpi.file_module.favourite.services;

import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.favourite.repository.FavouriteRepo;
import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AppService
public class FavouriteServiceImpl implements FavouriteService {
    private final FavouriteRepo favouriteRepo;

    public FavouriteServiceImpl(FavouriteRepo favouriteRepo) {
        this.favouriteRepo = favouriteRepo;
    }

    @Override
    public void changeFavour(UUID fileId) {
        var file = favouriteRepo.getByPubId(fileId)
                .orElseThrow(ResourceNotExistException::new);
        file.getDetails().setIsFavourite(!file.getDetails().getIsFavourite());
        favouriteRepo.save(file);
    }

    @Override
    public List<FileInfoDTO> getFavouriteFiles(String username) {
        return favouriteRepo.getAllByDetails_IsFavouriteAndRootOwnerUsername(true, username)
                .stream().map(FileInfo::mapToDTO)
                .collect(Collectors.toList());
    }
}
