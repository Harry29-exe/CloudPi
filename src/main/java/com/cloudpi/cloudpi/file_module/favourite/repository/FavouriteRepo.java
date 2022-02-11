package com.cloudpi.cloudpi.file_module.favourite.repository;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfoDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavouriteRepo extends JpaRepository<FileInfo, Long> {
    List<FileInfo> getAllByDetails_IsFavouriteAndRootOwnerUsername(Boolean isFavourite, String username);
    Optional<FileInfo> getByPubId(UUID fileId);
}
