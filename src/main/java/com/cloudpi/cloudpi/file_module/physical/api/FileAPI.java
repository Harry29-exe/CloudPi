package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
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

    @PutMapping(
            path = "file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    FileInfoDTO uploadNewFile(
            @RequestParam(defaultValue = "UNDEFINED") FileType fileType,
            @RequestParam String filepath,
            @RequestParam MultipartFile file,
            Authentication auth);

    //todo potrzebne?
    @PostMapping(
            path = "image/{imageName}",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    FileInfoDTO uploadNewImage(
            @PathVariable String imageName,
            @RequestBody MultipartFile file,
            Authentication auth);


    @GetMapping("file/{fileId}")
    Resource downloadFile(@PathVariable UUID fileId);


    @GetMapping("directory/{directoryId}")
    Resource compressAndDownloadDirectory(@PathVariable String directoryId);


    @GetMapping(path = "image-preview")
    List<Resource> getImagesPreview(
            @RequestParam(defaultValue = "64") Integer previewResolution,
            @RequestBody List<String> imageNames);


    @DeleteMapping("file/{fileId}")
    void deleteFile(@PathVariable UUID fileId);


    @DeleteMapping(
            path = "file",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void deleteFiles(@RequestBody @NotEmpty List<UUID> fileIds);


}