package com.cloudpi.cloudpi.file_module.favourite.repository;

import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavouriteRepo extends JpaRepository<FileInfo, Long> {
    List<FileInfo> getAllByDetails_IsFavouriteAndRootOwnerUsername(Boolean isFavourite, String username);
    Optional<FileInfo> getByPubId(UUID fileId);
}
