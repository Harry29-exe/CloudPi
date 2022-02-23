package com.cloudpi.cloudpi.file_module.permission.repositories;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilePermissionRepo extends JpaRepository<FilePermission, Long> {

    @Query("""
            SELECT COUNT(f) > 0
            FROM FileInfo f
            WHERE
                f.pubId = :filePubId
                AND
                (
                f.root.owner.username = :username
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.permissions p
                    WHERE
                        p.user.username = :username AND
                        p.type = :type
                )
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.ancestors a
                    JOIN a.file.permissions ap
                    WHERE
                        ap.user.username = :username AND
                        ap.type = :type
                )
                )
            """)
    Boolean hasPermission(UUID filePubId, String username, PermissionType type);

    @Query("""
            SELECT COUNT(f) > 0
            FROM FileInfo f
            WHERE
                f.path = :filePath
                AND
                (
                f.root.owner.username = :username
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.permissions p
                    WHERE
                        p.user.username = :username AND
                        p.type = :type
                )
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.ancestors a
                    JOIN a.file.permissions ap
                    WHERE
                        ap.user.username = :username AND
                        ap.type = :type
                )
                )
            """)
    Boolean hasPermissionByPath(String filePath, String username, PermissionType type);

    @Query("""
            SELECT COUNT(f) = LENGTH(:filePubIds)
            FROM FileInfo f
            WHERE
                f.pubId = :filePubId
                AND
                (
                f.root.owner.username = :username
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.permissions p
                    WHERE
                        p.user.username = :username AND
                        p.type = :type
                )
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.ancestors a
                    JOIN a.file.permissions ap
                    WHERE
                        ap.user.username = :username AND
                        ap.type = :type
                )
                )
            GROUP BY f
            """)
    Boolean hasPermission(List<UUID> filePubIds, String username, PermissionType type);

    @Query("""
            SELECT COUNT(f) > 0
            FROM FileInfo f
            WHERE
                f.path = :filePath
                AND
                (
                f.root.owner.username = :username
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.permissions p
                    WHERE
                        p.user.username = :username AND
                        p.type = :type
                )
                OR
                EXISTS (
                    SELECT TRUE
                    FROM f.ancestors a
                    JOIN a.file.permissions ap
                    WHERE
                        ap.user.username = :username AND
                        ap.type = :type
                )
                )
            """)
    Boolean hasPermissionByPath(List<String> filePath, String username, PermissionType type);

    @Query("""
            SELECT fp.type
            FROM FilePermission fp
            WHERE
                fp.user.username = :username AND
                fp.file.pubId = :filePubId
            """)
    List<PermissionType> findAllUserFilePermissions(String username, UUID filePubId);

    @Query("""
            SELECT new com.cloudpi.cloudpi.file_module.permission.repositories.UserPermissionView(fp.type, u.username)
            FROM FilePermission fp
            JOIN fp.user u
            WHERE
                fp.file.pubId = :filePubId
            """)
    List<UserPermissionView> findAllFilePermissions(UUID filePubId);

    void removeByUser_UsernameAndFile_PubIdAndTypeIn(String username, UUID filePubId, List<PermissionType> type);

    void removeByFile_PubId(UUID filePubId);

}
