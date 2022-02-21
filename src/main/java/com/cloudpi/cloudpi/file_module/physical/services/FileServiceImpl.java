package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.exception.file.*;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.UpdateVFile;
import com.cloudpi.cloudpi.file_module.permission.service.dto.CreateFile;
import com.cloudpi.cloudpi.utils.AppService;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@AppService
public class FileServiceImpl implements FileService {
    @Value("${cloud-pi.compression.storage}")
    private String compressionDirectory;
    private final DriveService driveService;
    private final FileInfoService fileInfoService;
    private final FileInfoRepo fileInfoRepo;

    public FileServiceImpl(DriveService driveService, FileInfoService fileInfoService, FileInfoRepo fileInfoRepo) {
        this.driveService = driveService;
        this.fileInfoService = fileInfoService;
        this.fileInfoRepo = fileInfoRepo;
    }

    @Override
    public FileInfoDTO create(CreateFile create, MultipartFile file) {
        var drive = driveService.getDriveForNewFile(file.getSize());
        var vPath = create.getPath();
        FileInfoDTO newFile;

        if(fileInfoRepo.existsByPath(vPath.getPath())) {
            newFile = modify(fileInfoRepo.getPubIdByPath(vPath.getPath()), file);
        } else {
            newFile = fileInfoService.save(new CreateFileInDB(
                    vPath,
                    drive.getPubId(),
                    create.getFileType(),
                    file.getSize()
            ));
            saveFileToDisc(file, drive.getPath() + "/" + newFile.getPubId());
        }

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

    @Override
    public ResponseEntity<byte[]> readPreview(Integer previewResolution, UUID imageId) {
        var path = driveService.getPathToFile(imageId);
        var file = path.toFile();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = bufferedImage.getScaledInstance(previewResolution, previewResolution, Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(previewResolution, previewResolution, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(image, 0, 0, null);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(outputImage, "jpg", os);
            return new ResponseEntity<>(os.toByteArray(), HttpStatus.OK);
        } catch(IOException ioex) {
            throw new CouldNotReadFileException();
        }
    }

    @Override
    public Resource downloadCompressedDirectory(UUID directoryID) {
        var rootDirectory = fileInfoRepo.findByPubId(directoryID)
                .orElseThrow(ResourceNotExistException::new);
        if(rootDirectory.getType() != FileType.DIRECTORY) {
            throw new NotDirectoryException();
        }

        String compressedPath = compressionDirectory + rootDirectory.getName() + ".zip";

        try {
            FileOutputStream fos = new FileOutputStream(compressedPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            createAndCompressChildFiles(rootDirectory, zos, new HashSet<>());
            Resource resource = new InputStreamResource(new FileInputStream(Paths.get(compressedPath).toFile()));
            Files.delete(Paths.get(compressedPath));
            return resource;
        } catch (IOException ioex) {
            throw new DirectoryCompressionException();
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

    private void createAndCompressChildFiles(FileInfo currentDirectory, ZipOutputStream zos, Set<String> pathSet)
            throws IOException {
        String allPath = currentDirectory.getPath();
        String zipPath = allPath.substring(allPath.indexOf(currentDirectory.getName()));
        zos.putNextEntry(new ZipEntry(zipPath + "/"));
        zos.closeEntry();

        for(var child : currentDirectory.getChildren()) {
            FileInfo currentFile = child.getFile();
            allPath = currentFile.getPath();
            zipPath = allPath.substring(allPath.indexOf(currentDirectory.getName()));
            Path currentPath = Paths.get(zipPath);
            if(!pathSet.contains(allPath)) {
                pathSet.add(allPath);
                if (currentFile.getType() != FileType.DIRECTORY) {
                    Path filePath = Paths.get(currentFile.getDrive().getPath() + "/" + currentFile.getPubId());
                    compressFile(filePath, currentPath, zos);
                }
            }
        }
    }

    private void compressFile(Path sourceFilePath, Path targetFilePath, ZipOutputStream zos)
            throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath.toFile());
        ZipEntry zipEntry = new ZipEntry(targetFilePath.toString());
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        fis.close();
        zos.closeEntry();
    }

}
