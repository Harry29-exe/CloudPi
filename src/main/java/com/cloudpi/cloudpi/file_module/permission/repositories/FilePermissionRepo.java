package com.cloudpi.cloudpi.file_module.permission.repositories;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FilePermissionRepo extends JpaRepository<FilePermission, Long> {

    @Query("""
            SELECT COUNT(fs) > 0
            FROM FilePermission fs
            JOIN fs.file f
            JOIN fs.user u
            WHERE
                f.pubId = :filePubId AND
                u.username = :username AND
                fs.type = :type
            """)
    Boolean hasPermission(UUID filePubId, String username, PermissionType type);


}
