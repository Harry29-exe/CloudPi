package com.cloudpi.cloudpi.file_module.filesystem.repositories;

import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import org.springframework.data.domain.Pageable;
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

    @Query("""
                SELECT f
                FROM FileInfo f
                WHERE f.root.id = :rootId
            """)
    List<FileInfo> findByRootId(long rootId);

    Boolean existsByPath(String path);

    @Query("""
                    SELECT f.pubId
                    FROM FileInfo f
                    WHERE f.path = :path
            """)
    UUID getPubIdByPath(String path);

    @Query("""
                    SELECT f
                    FROM FileInfo f
                    LEFT JOIN f.ancestors a
                    LEFT JOIN a.ancestor parent_file
                    WHERE (
                        (parent_file.path = :entryPointPath AND
                        a.treeLevelDiff <= :depth)
                        OR
                        f.path = :entryPointPath)
            """)
    List<FileInfo> findAllByFilestructure(String entryPointPath, Integer depth);

    @Query("""
            SELECT DISTINCT f
            FROM FileInfo f
            JOIN f.permissions fp
            WHERE
                fp.user.username = :username
            """)
    List<FileInfo> findAllSharedToUser(String username);

    @Query("""
            SELECT DISTINCT f
            FROM FileInfo f
            JOIN f.permissions fp
            WHERE
                fp.user.username = :username
            """)
    List<FileInfo> findAllSharedToUser(String username, Pageable pageable);

    @Query("""
            SELECT DISTINCT f
            FROM FileInfo f
            JOIN f.permissions fp
            WHERE
                f.root.owner.username = :username AND
                fp.user.username <> :username
            """)
    List<FileInfo> findAllSharedByUser(String username);

    @Query("""
            SELECT DISTINCT f
            FROM FileInfo f
            JOIN f.permissions fp
            WHERE
                f.root.owner.username = :username AND
                fp.user.username <> :username
            """)
    List<FileInfo> findAllSharedByUser(String username, Pageable pageable);


    @Query("""
                SELECT f.pubId
                FROM FileInfo f
                WHERE f.root.owner.username = :username
                AND f.type <> :directory
            """)
    List<UUID> getUsersFilesIds(String username, FileType directory);

    @Query("""
                SELECT f.pubId
                FROM FileInfo f
                WHERE f.root.owner.username = :username
                AND f.parentId is not null
                ORDER BY f.parentId DESC
            """)
    List<UUID> getAllUsersFiles(String username);

    @Transactional
    @Modifying
    void deleteByPubId(UUID pubId);
}