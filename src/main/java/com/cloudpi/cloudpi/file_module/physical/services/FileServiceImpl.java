package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.exception.file.CouldNotDeleteFileException;
import com.cloudpi.cloudpi.exception.file.CouldNotModifyFileException;
import com.cloudpi.cloudpi.exception.file.CouldNotReadFileException;
import com.cloudpi.cloudpi.exception.file.CouldNotSaveFileException;
import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FilesystemRootInfoRepo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FilesystemInfoService;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;
import com.cloudpi.cloudpi.utils.AppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public FileInfoDTO create(CreateFile create, MultipartFile file) {
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
        var path = driveService.getPathToFile(filePubId);
        var fileToModify = path.toFile();
        if(fileToModify.isDirectory()) {
            throw new CouldNotModifyFileException("You cannot modify directories with this endpoint");
        }
        try {
            FileOutputStream stream = new FileOutputStream(fileToModify, false);
            stream.write(file.getBytes());
            stream.close();
            return fileInfoService.update(new UpdateVFile(filePubId, file.getName()));
        } catch (IOException ioex) {
            throw new CouldNotModifyFileException("There is no such file");
        }
    }

    @Override
    public void delete(UUID filePubId) {
        var path = driveService.getPathToFile(filePubId);
        var fileToDelete = path.toFile();
        if(fileToDelete.isDirectory()) {
            throw new CouldNotDeleteFileException("This endpoint is prepared to delete files, not directories. " +
                    "Use filesystem delete endpoint instead.");
        }
        boolean couldDelete = fileToDelete.delete();
        if(!couldDelete) {
            throw new CouldNotDeleteFileException("Could not delete specified file");
        }
        fileInfoService.delete(filePubId);
    }

    // todo dokladnie przetestowac i zoptymalizowac te metode
    @Override
    public ResponseEntity<byte[]> readPreview(Integer previewResolution, UUID imageId) {
        var path = driveService.getPathToFile(imageId);
        var file = path.toFile();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = bufferedImage.getScaledInstance(previewResolution, previewResolution, Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(previewResolution, previewResolution, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(image, 0, 0, null);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "jpg", os);
            return new ResponseEntity<>(os.toByteArray(), HttpStatus.OK);
        } catch(IOException ioex) {
            throw new CouldNotReadFileException();
        }
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



