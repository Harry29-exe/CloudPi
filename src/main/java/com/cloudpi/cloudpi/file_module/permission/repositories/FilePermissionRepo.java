package com.cloudpi.cloudpi.file_module.permission.repositories;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.permission.entities.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
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

    //    @Query("""
//            SELECT fs.type
//            FROM FilePermission fs
//            WHERE
//                fs.user.username = :username AND
//                fs.file.pubId = :filePubId
//    """)
    List<PermissionType> findAllByUser_UsernameAndFile_PubId(String username, UUID filePubId);

    //    @Query("""
//            SELECT u.username, f.pubId, fs.type
//            FROM FilePermission fs
//            JOIN fs.user u
//            JOIN fs.file f
//            WHERE
//                u.username = :username AND
//                f.pubId IN :filePubId
//    """)
    List<FilePermissionProjection> findAllByUser_UsernameAndFile_PubIdIn(String username, List<UUID> filePubId);

    List<FilePermissionProjection> findAllByFile_PubId(UUID filePubId);

    List<FilePermissionProjection> findAllByFile_PubIdIn(Collection<UUID> file_pubId);


    interface FilePermissionProjection {

        UserProjection getUser();

        FileProjection getFile();

        PermissionType getType();

        interface FileProjection {
            UUID getPubId();
        }

        interface UserProjection {
            String getUsername();
        }

        default UUID getFilePubId() {
            return getFile().getPubId();
        }

        default String getUsername() {
            return getUser().getUsername();
        }

    }


}
