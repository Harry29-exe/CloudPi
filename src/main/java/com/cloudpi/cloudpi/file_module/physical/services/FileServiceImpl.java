package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileServiceImpl implements FileService {

    @Override
    public FileInfoDTO save(CreateFile create, MultipartFile file) {
        return null;
    }

    @Override
    public Resource read(UUID filePubId) {
        return null;
    }

    @Override
    public FileInfoDTO modify(UUID filePubId, MultipartFile file) {
        return null;
    }

    @Override
    public void delete(UUID filePubId) {

    }

}
