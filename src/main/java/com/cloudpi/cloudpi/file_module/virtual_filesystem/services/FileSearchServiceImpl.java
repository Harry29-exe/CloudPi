package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileSearchQueryDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.List;

@AppService
public class FileSearchServiceImpl implements FileSearchService {
    private final FileInfoRepo fileInfoRepo;

    public FileSearchServiceImpl(FileInfoRepo fileInfoRepo) {
        this.fileInfoRepo = fileInfoRepo;
    }

    @Override
    public List<FileInfoDTO> find(FileSearchQueryDTO fileQuery) {
//        fileInfoRepo.findBy()
        return null;
    }
}
