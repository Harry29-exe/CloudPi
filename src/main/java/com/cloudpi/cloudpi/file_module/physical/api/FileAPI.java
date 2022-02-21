package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@RequestMapping("/files/")
@Tag(name = "File API",
        description = "API for uploading, downloading and deleting files")
public interface FileAPI {

    @PostMapping(
            path = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "uploads new file, or modify it if exist",
            description = "Uploads new file to the filesystem, if file exist it's modified")
    FileInfoDTO uploadNewFile(
            @RequestParam(defaultValue = "UNDEFINED") FileType fileType,
            @RequestParam String filepath,
            @RequestParam MultipartFile file,
            Authentication auth);

    @PostMapping(
            path = "image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "uploads new image",
            description = "Uploads new image to ~/images/ directory")
    FileInfoDTO uploadNewImage(
            @RequestParam MultipartFile file,
            Authentication auth);


    @GetMapping("file/{fileId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "downloads a file",
            description = "Returns a binary of file with provided UUID")
    Resource downloadFile(@PathVariable UUID fileId);


    @GetMapping("directory/{directoryId}")
    @Operation(summary = "compresses and returns a selected directory",
            description = "compresses a directory with provided public id and returns a byte stream of it")
    Resource compressAndDownloadDirectory(@PathVariable String directoryId);


    @GetMapping(path = "image-preview")
    @Operation(summary = "creates image preview with provided resolution",
            description = "returns base64 encoded resized images for files with provided UUIDs in the body")
    List<byte[]> getImagesPreview(
            @RequestParam(defaultValue = "64") Integer previewResolution,
            @RequestBody List<UUID> imageIds);


    @DeleteMapping("file/{fileId}")
    @Operation(summary = "deletes a file with provided UUID")
    void deleteFile(@PathVariable UUID fileId);


    @DeleteMapping(
            path = "file",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "deletes files with provided UUIDs in the body")
    void deleteFiles(@RequestBody @NotEmpty List<UUID> fileIds);


}