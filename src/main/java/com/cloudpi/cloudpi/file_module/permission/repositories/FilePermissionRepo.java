package com.cloudpi.cloudpi.file_module.permission.repositories;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import com.cloudpi.cloudpi.file_module.permission.service.FilePermissionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FilePermissionRepo extends JpaRepository<FilePermission, Long> {

    @Query("""
        SELECT COUNT(fs) > 0
        FROM FilePermission fs
        JOIN VFile f
        JOIN UserEntity u
        WHERE
            f.pubId = :filePubId AND
            u.username = :username AND
            fs.type = :type
        """)
    Boolean permissionExists(UUID filePubId, String username, PermissionType type);

}
