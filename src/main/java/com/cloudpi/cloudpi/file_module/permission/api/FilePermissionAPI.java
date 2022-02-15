package com.cloudpi.cloudpi.file_module.permission.api;

import com.cloudpi.cloudpi.config.springdoc.NotImplemented;
import com.cloudpi.cloudpi.file_module.permission.api.requests.DeleteAllPermissionsRequest;
import com.cloudpi.cloudpi.file_module.permission.api.requests.DeletePermissionsRequest;
import com.cloudpi.cloudpi.file_module.permission.api.requests.PostAddPermissionRequest;
import com.cloudpi.cloudpi.file_module.permission.dto.FilePermissionsDTO;
import com.cloudpi.cloudpi.file_module.permission.dto.UserFilePermissionsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
