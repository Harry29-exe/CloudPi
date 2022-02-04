package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

public interface FilesystemInfoService {

    void createVirtualFilesystem(Long userId, Long driveSize);

    void createVirtualFilesystem(Long userId);

}
