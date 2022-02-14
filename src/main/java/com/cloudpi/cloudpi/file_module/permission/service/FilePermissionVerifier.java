package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public interface FilePermissionVerifier {

    boolean canModify(UUID filePubId);

    boolean canModify(String path);

    boolean canModify(VirtualPath path);

    boolean canRead(UUID filePubId);

    boolean canRead(String path);

    boolean canRead(VirtualPath path);
}
