package com.cloudpi.cloudpi.file_module;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.filesystem.services.FileInfoService;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.CreateFileInDB;
import com.cloudpi.cloudpi.file_module.physical.services.DriveService;
import com.cloudpi.cloudpi.file_module.physical.services.FileService;
import com.cloudpi.cloudpi.file_module.physical.services.dto.CreateDrive;
import com.cloudpi.cloudpi.utils.component_tests.ComponentTestTemplate;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public abstract class FileComponentTestTemplate extends ComponentTestTemplate {

    @Autowired
    protected FileService fileService;
    @Autowired
    protected FileInfoService fileInfoService;
    @Autowired
    protected DriveService driveService;

    @Value("${cloud-pi.storage.mock.save-files-dir}")
    private String storageMockPath;

    protected CreateDrive drive = new CreateDrive(
            _getStoragePath().toString(),
            (long) 1e9
    );

    protected List<CreateFileInDB> createFileInDBList;

    protected void initTemplate() {
        initUsersInDB();
    }

    protected void initFileStructure() {
        var dirs = _createDirPathsList();
        for (var dir : dirs) {
            fileService.createDir(new VirtualPath(dir));
        }

        var files = _createFilesInDBList();
        for (var file : files) {
            fileInfoService.save(file);
        }
    }

    protected void initDrive() {
        driveService.add(drive);
    }

    protected List<String> _createDirPathsList() {
        return ImmutableList.of(
                "bob/dir1",
                "bob/dir2",
                "bob/dir1/dir11"
        );
    }

    protected List<CreateFileInDB> _createFilesInDBList() {
        var drive = driveService.getDriveForNewFile(1L);
        return ImmutableList.of(
                new CreateFileInDB(
                        new VirtualPath("bob/file1"),
                        drive.getPubId(),
                        FileType.TEXT_FILE,
                        10000L
                )
        );
    }

    protected Path _getStoragePath() {
        if (storageMockPath.startsWith("~")) {
            var path = System.getProperty("user.home") + storageMockPath.substring(1);
            return Paths.get(path);
        }

        return Paths.get(storageMockPath);
    }

}
