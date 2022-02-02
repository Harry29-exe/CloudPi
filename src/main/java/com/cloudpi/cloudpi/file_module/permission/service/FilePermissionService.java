package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VPath;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public interface FilePermissionService {

    boolean canModify(UUID filePubId);

//    boolean canModify(UUID filePubId, String username);

    boolean canModify(String path);

    boolean canModify(VPath path);

//    boolean canModify(String path, String username);

    boolean canRead(UUID filePubId);

//    boolean canRead(UUID filePubId, String username);

    boolean canRead(String path);

    boolean canRead(VPath path);

//    boolean canRead(String path, String username);

}
