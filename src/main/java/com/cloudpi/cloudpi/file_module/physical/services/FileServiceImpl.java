package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.exception.file.CouldNotReadFileException;
import com.cloudpi.cloudpi.exception.file.CouldNotSaveFileException;
import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@AppService
public class FileServiceImpl implements FileService {
    private final DriveService driveService;
    private final FileInfoService fileInfoService;

    public FileServiceImpl(DriveService driveService, FileInfoService fileInfoService) {
        this.driveService = driveService;
        this.fileInfoService = fileInfoService;
    }

    @Override
    public FileInfoDTO save(CreateFile create, MultipartFile file) {
        var drive = driveService.getDriveForNewFile(file.getSize());
        var vPath = create.getPath();

        var newFile = fileInfoService.save(new CreateFileInDB(
                vPath,
                drive.getPubId(),
                create.getFileType(),
                file.getSize()
        ));

        saveFileToDisc(file, drive.getPath() + newFile.getPubId());


        return newFile;
    }

    @Override
    public FileInfoDTO createDir(VirtualPath path) {
        return fileInfoService.saveDir(path);
    }

    @Override
    public Resource read(UUID filePubId) {
        var path = driveService.getPathToFile(filePubId);

        try {
            return new InputStreamResource(new FileInputStream(path.toFile()));

        } catch (IOException ioex) {
            throw new CouldNotReadFileException();
        }
    }

    @Override
    public FileInfoDTO modify(UUID filePubId, MultipartFile file) {
        return null;
    }

    @Override
    public void delete(UUID filePubId) {

    }

    protected void saveFileToDisc(MultipartFile inFile, String newFilePath) {
        var path = Paths.get(newFilePath);
        try {
            path.toFile().createNewFile();
        } catch (IOException ioex) {
            throw new CouldNotSaveFileException();
        }

        try {
            inFile.transferTo(path);
        } catch (IOException ioex) {
            try {
                Files.copy(inFile.getInputStream(), path);
            } catch (IOException ioex2) {
                //todo jakoś bardziej to zabezpieczyć (w sumie to całą metodę)
                path.toFile().delete();
                throw  new CouldNotSaveFileException();
            }
        }
    }

}



