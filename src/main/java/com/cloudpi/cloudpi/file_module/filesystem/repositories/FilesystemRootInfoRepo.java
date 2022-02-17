package com.cloudpi.cloudpi.file_module.filesystem.repositories;

import com.cloudpi.cloudpi.file_module.filesystem.domain.FilesystemInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesystemRootInfoRepo extends JpaRepository<FilesystemInfo, Long> {

    Optional<FilesystemInfo> findByOwner_Id(Long id);

    Optional<FilesystemInfo> findByOwner_Username(String username);

    List<FilesystemInfo> findAllByOwner_Username(String username);

    void deleteByOwner_Username(String username);
}