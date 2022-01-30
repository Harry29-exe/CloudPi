package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.repositories;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.VFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VFileRepo extends JpaRepository<VFile, Long> {

    Optional<VFile> findByPath(String path);

    Optional<VFile> findByPubId(UUID pubId);

    //todo test it
    @Transactional
    @Modifying
    @Query("""
            UPDATE VFile f
            SET f.path = CONCAT(:newPath, SUBSTRING(f.path, LENGTH(:newPath) + 1))
            WHERE f.path LIKE CONCAT(:newPath, '%')
    """)
    void moveDirectory(String newPath, String oldPath);

    //@Transactional
    //@Modifying
    //@Query("""
    //        UPDATE FileEntity f
    //        SET f.size = :newSize,
    //            f.modifiedAt = :updateDate
    //        WHERE f.id = :id
    //        """)
    //void updateFileSize(UUID id, Long newSize, Date updateDate);

}