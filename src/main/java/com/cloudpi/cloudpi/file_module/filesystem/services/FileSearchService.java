package com.cloudpi.cloudpi.file_module.filesystem.services;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileQueryDTO;

import java.util.List;

public interface FileSearchService {

    List<FileInfoDTO> find(FileQueryDTO fileQuery);

}
