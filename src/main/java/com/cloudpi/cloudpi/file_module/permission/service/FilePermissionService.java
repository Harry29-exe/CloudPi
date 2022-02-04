package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public interface FilePermissionService {

    boolean canModify(UUID filePubId);

//    boolean canModify(UUID filePubId, String username);

    boolean canModify(String path);

    boolean canModify(VirtualPath path);

//    boolean canModify(String path, String username);

    boolean canRead(UUID filePubId);

//    boolean canRead(UUID filePubId, String username);

    boolean canRead(String path);

    boolean canRead(VirtualPath path);

//    boolean canRead(String path, String username);

}
