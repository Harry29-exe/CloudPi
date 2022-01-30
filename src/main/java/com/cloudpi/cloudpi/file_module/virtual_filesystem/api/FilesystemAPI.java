package com.cloudpi.cloudpi.file_module.virtual_filesystem.api;

import com.cloudpi.cloudpi.config.springdoc.SpringDocUtils;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.api.dto.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFileDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.VFileStructureDTO;
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


    @PostMapping("directory")
    VFileDTO createDirectory(
            @RequestParam String directoryPath,
            Authentication auth);

    @GetMapping("file-structure")
    VFileStructureDTO getFileStructure(
            @RequestParam(defaultValue = "0") Integer structureLevels,
            @RequestParam(defaultValue = "/") String fileStructureRoot,
            Authentication auth);


    @GetMapping("file/{fileId}")
    VFileDTO getFileInfo(
            @PathVariable("fileId") String fileId,
            @PathVariable(name = "with-permissions", required = false)
                    Boolean getWithPermissions);


    @PatchMapping("move")
    void moveFile(@RequestBody @Valid MoveFileRequest requestBody);


    @GetMapping("directory/{dirId}")
    DirectoryDto getDirInfo(
            @PathVariable("fileId") String fileId,
            @PathVariable(name = "with-permissions", required = false)
                    Boolean getWithPermissions);


    @DeleteMapping("directory/{directoryId}")
    void deleteDirectory(@PathVariable String directoryId);


    //todo do innego api
    @GetMapping("user-drive")
    List<GetUserDriveInfo> getUsersVirtualDrivesInfo(
            @PathVariable List<String> usernames);


    @PostMapping("user-drive")
    void changeVirtualDriveMaxSize(
            @PathVariable String username,
            @RequestParam Long newAssignedSpace
    );
}
