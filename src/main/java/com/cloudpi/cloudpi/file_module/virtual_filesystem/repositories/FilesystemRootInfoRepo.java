package com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FilesystemRootInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesystemRootInfoRepo extends JpaRepository<FilesystemRootInfo, Long> {

    Optional<FilesystemRootInfo> findByOwner_Id(Long id);

    Optional<FilesystemRootInfo> findByOwner_Username(String username);
    List<FilesystemRootInfo> findAllByOwner_Username(String username);

}