package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
public class FileAPIController implements FileAPI {

    @Override
    public void uploadNewFile(FileType fileType, String filepath, MultipartFile file, Authentication auth) {

    }

    @Override
    public void uploadNewImage(String imageName, byte[] image, Authentication auth) {

    }

    @Override
    public Resource downloadFile(String fileId) {
        return null;
    }

    @Override
    public Resource compressAndDownloadDirectory(String directoryId) {
        return null;
    }

    @Override
    public List<Resource> getImagesPreview(Integer previewResolution, List<String> imageNames) {
        return null;
    }

    @Override
    public void deleteDirectory(String fileId) {

    }

    @Override
    public void deleteFile(UUID fileId) {

    }

    @Override
    public void deleteFiles(List<UUID> fileId) {

    }
}
