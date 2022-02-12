package com.cloudpi.cloudpi.file_module.sharing.dto;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserSharedFilesDTO {

    private String username;
    private List<FileInfoDTO> sharedFiles;
}
