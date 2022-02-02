package com.cloudpi.cloudpi.file_module.virtual_filesystem.api;

import com.cloudpi.cloudpi.config.springdoc.SpringDocUtils;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.api.request.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FileStructureDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/filesystem/")
@Tag(name = "Filesystem API",
        description = SpringDocUtils.NOT_IMPLEMENTED +
                "API for retrieving user's file structure and file/directory" +
                " info and modify it.")
public interface FilesystemAPI {


    @GetMapping("file-structure")
    FileStructureDTO getFileStructure(
            @RequestParam(defaultValue = "0") Integer structureLevels,
            @RequestParam(defaultValue = "/") String fileStructureRoot,
            Authentication auth);

    @PutMapping("directory")
    FileInfoDTO createDirectory(
            @RequestParam String directoryPath,
            Authentication auth);

    @GetMapping("file/{fileId}")
    FileInfoDTO getFileInfo(
            @PathVariable("fileId") String fileId,
            @RequestParam(name = "with-permissions", defaultValue = "false")
                    Boolean getWithPermissions);


    @PatchMapping("move")
    void moveFile(@RequestBody @Valid MoveFileRequest requestBody);


    @DeleteMapping("directory/{directoryId}")
    void deleteDirectory(@PathVariable String directoryId);


    @GetMapping("{username}")
    List<FilesystemInfoDTO> getUsersVirtualDrivesInfo(
            @PathVariable String username);


    @PostMapping("{username}")
    void changeVirtualDriveMaxSize(
            @PathVariable String username,
            @RequestParam Long newAssignedSpace
    );
}
