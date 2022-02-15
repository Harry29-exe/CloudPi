package com.cloudpi.cloudpi.file_module.permission.repositories;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

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
    Boolean hasPermission(String filePath, String username, PermissionType type);

    @Query("""
            SELECT fp.type
            FROM FilePermission fp
            WHERE
                fp.user.username = :username AND
                fp.file.pubId = :filePubId
            """)
    List<PermissionType> findAllUserFilePermissions(String username, UUID filePubId);

    @Query("""
            SELECT new com.cloudpi.cloudpi.file_module.permission.repositories.FilePermissionRepo.UserPermissionView(fp.type, u.username)
            FROM FilePermission fp
            JOIN fp.user u
            WHERE
                fp.file.pubId = :filePubId
            """)
    List<UserPermissionView> findAllFilePermissions(UUID filePubId);

    void removeByUser_UsernameAndFile_PubIdAndType(String username, UUID filePubId, PermissionType type);

    @Getter
    @Setter
    @AllArgsConstructor
    class UserPermissionView {
        private PermissionType type;
        private String username;
    }

}
