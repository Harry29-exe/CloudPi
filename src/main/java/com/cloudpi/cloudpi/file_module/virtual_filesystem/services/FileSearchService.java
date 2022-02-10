package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileSearchQueryDTO;

import java.util.List;

public interface FileSearchService {

    List<FileInfoDTO> find(FileSearchQueryDTO fileQuery);

}
