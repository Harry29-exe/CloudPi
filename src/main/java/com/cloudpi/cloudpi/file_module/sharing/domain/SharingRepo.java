package com.cloudpi.cloudpi.file_module.sharing.domain;

import com.cloudpi.cloudpi.file_module.sharing.dto.UserSharedFilesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharingRepo extends JpaRepository<Sharing, Long> {
    List<Sharing> getAllByPermission_User_Username(String username);
}
