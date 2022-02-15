package com.cloudpi.cloudpi.file_module.permission.api;

import com.cloudpi.cloudpi.config.springdoc.NotImplemented;
import com.cloudpi.cloudpi.file_module.permission.api.requests.DeleteAllPermissionsRequest;
import com.cloudpi.cloudpi.file_module.permission.api.requests.PostAddPermissionRequest;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "FilePermissionAPI",
        description = "Api for managing permissions to files.")
@RequestMapping("/file-permission/")
@Validated
public interface FilePermissionAPI {

    @Operation(summary = NotImplemented.PRIORITY_LOW + "Returns permissions that user have to given file.",
            description = "Based on logged user returns user's permissions" +
                    "to file with given id")
    @GetMapping("my-permission/{filePubId}")
    UserFilePermissionsDTO getUsersPermissions(@PathVariable UUID filePubId);

    @Operation(summary = NotImplemented.PRIORITY_LOW + "Returns list of all permissions assigned to given file",
            description = "Returns list of all permissions that users have to given file")
    @GetMapping("permissions/{filePubId}")
    FilePermissionsDTO getFilePermissions(@PathVariable String filePubId);

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

    @PostMapping("permissions")
    @Operation(summary = "Add given permission to given file and user")
    void addPermissions(@RequestBody PostAddPermissionRequest request);

    @DeleteMapping("permissions")
    @Operation(summary = NotImplemented.PRIORITY_MEDIUM + "Removes permission with parameters in request body")
    void revokePermissions(@RequestBody DeletePermissionsRequest request);

    @DeleteMapping("permissions/all")
    @Operation(summary = "Removes permission all parameters for specified user and file")
    void revokeAllPermissions(@RequestBody DeleteAllPermissionsRequest request);


}
