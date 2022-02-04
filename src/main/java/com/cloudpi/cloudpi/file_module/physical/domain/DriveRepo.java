package com.cloudpi.cloudpi.file_module.physical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriveRepo extends JpaRepository<Drive, UUID> {

    @Query("""
        SELECT d.path
        FROM Drive d
        JOIN d.files f
        WHERE f.pubId = :filePubId
        """)
    Drive findByAssignedFile(UUID filePubId);

}
