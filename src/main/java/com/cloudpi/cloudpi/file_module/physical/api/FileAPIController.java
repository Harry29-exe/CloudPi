package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.physical.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class FileAPIController implements FileAPI {
    private final FileService fileService;

    public FileAPIController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public FileInfoDTO uploadNewFile(
            FileType fileType,
            String filepath,
            MultipartFile file,
            Authentication auth
    ) {
        return fileService.create(new CreateFile(
                new VirtualPath(filepath),
                fileType
        ), file);
    }

    @Override
    public FileInfoDTO uploadNewImage(
            MultipartFile file,
            Authentication auth
    ) {
        return fileService.create(
                new CreateFile(
                        new VirtualPath(auth.getName() + "/images/" + file.getOriginalFilename()),
                        FileType.IMAGE
                ),
                file
        );

    }

    @Override
    public Resource downloadFile(UUID fileId, HttpServletResponse response) {
        var file = fileService.read(fileId);
//        file.getFile().length()

        String contentLength;
        var rawFile = file.getFile();
        var length = rawFile.length();
        contentLength = length + "";
        response.addHeader("Content-Length", contentLength);

        return file;
    }

    @Override
    public Resource compressAndDownloadDirectory(String directoryId) {
        return fileService.downloadCompressedDirectory(UUID.fromString(directoryId));
    }

    @Override
    public List<byte[]> getImagesPreview(Integer previewResolution, List<UUID> imageIds) {
        List<byte[]> resources = new ArrayList<>();
        for (UUID imageId : imageIds) {
            resources.add(fileService.readPreview(previewResolution, imageId).getBody());
        }
        return resources;
    }

    @Override
    public void deleteFile(UUID fileId) {
        fileService.delete(fileId);
    }

    @Override
    public void deleteFiles(List<UUID> fileIds) {
        for (var fileId: fileIds) {
            fileService.delete(fileId);
        }
    }
}
