package com.cloudpi.cloudpi.file_module.physical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriveRepo extends JpaRepository<Drive, UUID> {


}
