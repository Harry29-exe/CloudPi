package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.config.springdoc.NotImplemented;
import com.cloudpi.cloudpi.config.springdoc.Stability;
import com.cloudpi.cloudpi.config.springdoc.NotImplemented;
import com.cloudpi.cloudpi.config.springdoc.Stability;
import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.physical.services.FileService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @Stability.InitialTests
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
    @Stability.EarlyDevelopment
    public FileInfoDTO uploadNewImage(
            MultipartFile file,
            Authentication auth
    ) {
        //todo raczej nie tak
        return fileService.create(
                new CreateFile(
                        new VirtualPath(auth.getName() + "/images/" + file.getOriginalFilename()),
                        FileType.IMAGE
                ),
                file
        );

    }

    @Override
    @Stability.EarlyDevelopment
    public Resource downloadFile(UUID fileId) {
        return fileService.read(fileId);
    }

    @Override
    @NotImplemented.LOW
    public Resource compressAndDownloadDirectory(String directoryId) {
        throw new NotYetImplementedException();
    }

    @Override
    @Stability.EarlyDevelopment
    public List<byte[]> getImagesPreview(Integer previewResolution, List<UUID> imageIds) {
        List<byte[]> resources = new ArrayList<>();
        for (UUID imageId : imageIds) {
            resources.add(fileService.readPreview(previewResolution, imageId).getBody());
        }
        return resources;
    }

    @Override
    @Stability.EarlyDevelopment
    public void deleteFile(UUID fileId) {
        fileService.delete(fileId);
    }

    @Override
    @Stability.EarlyDevelopment
    public void deleteFiles(List<UUID> fileIds) {
        for (var fileId: fileIds) {
            fileService.delete(fileId);
        }
    }
}
