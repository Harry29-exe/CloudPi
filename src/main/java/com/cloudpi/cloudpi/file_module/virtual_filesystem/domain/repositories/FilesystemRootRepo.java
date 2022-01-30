package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.repositories;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.VFilesystemRoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesystemRootRepo extends JpaRepository<VFilesystemRoot, Long> {

    Optional<VFilesystemRoot> findByOwner_Id(Long id);

    Optional<VFilesystemRoot> findByOwner_Username(String username);

}