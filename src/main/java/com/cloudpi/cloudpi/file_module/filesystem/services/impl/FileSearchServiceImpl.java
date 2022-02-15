package com.cloudpi.cloudpi.file_module.filesystem.services.impl;

import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FileSearchRepo;
import com.cloudpi.cloudpi.file_module.filesystem.services.FileSearchService;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.List;

@AppService
public class FileSearchServiceImpl implements FileSearchService {
    private final FileSearchRepo fileSearchRepo;

    public FileSearchServiceImpl(FileSearchRepo fileSearchRepo) {
        this.fileSearchRepo = fileSearchRepo;
    }

    @Override
    public List<FileInfoDTO> find(FileQueryDTO fileQuery) {
        var results = fileSearchRepo
                .findByQuery(fileQuery);
        return results
                .stream()
                .map(FileInfo::mapToDTO)
                .toList();
    }

}
