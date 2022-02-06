package com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileInfoRepo extends JpaRepository<FileInfo, Long> {

    Optional<FileInfo> findByPath(String path);

    Optional<FileInfo> findByPubId(UUID pubId);

    //todo test it
    @Transactional
    @Modifying
    @Query("""
                    UPDATE FileInfo f
                    SET f.path = CONCAT(:newPath, SUBSTRING(f.path, LENGTH(:newPath) + 1))
                    WHERE f.path LIKE CONCAT(:oldPath, '%')
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

    @Query("""
                SELECT f
                FROM FileInfo f
                WHERE f.root.id = :rootId
            """)
    List<FileInfo> findByRootId(long rootId);
}