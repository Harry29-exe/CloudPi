package com.cloudpi.cloudpi.file_module.permission.service;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    boolean canReadAllByPubId(List<UUID> filePubId);

    boolean canReadAllByPath(List<String> path);

    boolean canReadAllByVirtualPath(List<VirtualPath> path);

}
