package com.cloudpi.cloudpi.file_module.filesystem.api;

import com.cloudpi.cloudpi.config.springdoc.NotImplemented;
import com.cloudpi.cloudpi.file_module.filesystem.api.request.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.structure.FileStructureDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/filesystem/")
@Tag(name = "Filesystem API",
        description = "API for retrieving user's file structure and file/directory" +
                " info and modify it.")
public interface FilesystemAPI {

    //todo fix it
    @GetMapping("file-structure")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "retrieves file structure",
            description = "Returns file structure based on requested depth level and root - " +
                    "if a file has no children or depth level is too low to show them, children value is returned as empty list")
    FileStructureDTO getFileStructure(
            @RequestParam(defaultValue = "0") Integer structureLevels,
            @RequestParam(defaultValue = "/") String fileStructureRoot,
            Authentication auth);


    @Operation(summary = NotImplemented.PRIORITY_MEDIUM + "Returns list of files that user shared",
            description = "Returns list of files that belong to logged user and" +
                    "can be accessed by someone else other that")
    @GetMapping("files-shared-by-user")
    List<FileInfoDTO> getFilesSharedByUser();


    @GetMapping("files-shared-to-user")
    @Operation(summary = "Returns list of files that was shared to user",
            description = "Returns list of files that belong to other user but" +
                    "can be accessed by currently logged user")
    List<FileInfoDTO> getFilesSharedToUser();


    @PutMapping("directory")
    @Operation(summary = "creates a directory",
            description = "Creates a new directory at directory provided as request parameter")
    FileInfoDTO createDirectory(
            @RequestParam String directoryPath,
            Authentication auth);


    @GetMapping("file/{fileId}")
    @Operation(summary = "retrieves information about requested file")
    FileInfoDTO getFileInfo(
            @PathVariable("fileId") UUID fileId,
            @RequestParam(name = "with-permissions", defaultValue = "false")
                    Boolean getWithPermissions);


    @PatchMapping("move")
    @Operation(summary = "moves file",
            description = "Moves a file to place specified in path - possible file rename with this method")
    void moveFile(@RequestBody @Valid MoveFileRequest requestBody);


    @DeleteMapping("directory/{directoryId}")
    @Operation(summary = "deletes directory with provided id")
    void deleteDirectory(@PathVariable String directoryId);


    @GetMapping("{username}")
    @Operation(summary = "retrieves info about virtual drive for specified user",
            description = "Return information about filesystem for user with username provided in path")
    FilesystemInfoDTO getUsersFilesystemInfo(
            @PathVariable String username);


    @PostMapping("{username}")
    @Operation(summary = "changes max space for virtual drive",
            description = "Changes max space for filesystem for user with provided username - " +
                    "new assigned space has to be greater or equal to the space filled by all files")
    void changeUserFilesystemMaxSize(
            @PathVariable String username,
            @RequestParam Long newAssignedSpace
    );


    @PostMapping("search")
    List<FileInfoDTO> searchInUserFiles(
            @RequestBody FileQueryDTO searchQuery,
            Authentication auth);


}
