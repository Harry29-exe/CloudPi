package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.FileModuleAPITestTemplate;
import com.cloudpi.cloudpi.utils.IllegalTestStateException;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.UUID;

@ControllerTest
public class FileAPITestTemplate extends FileModuleAPITestTemplate {

    /**
     * <ul>
     *      <li>clears mock storage dir</li>
     *      <li>add default drive</li>
     *      <li>add default users (bob, Alice)</li>
     *      <li>add bob fileStructure</li>
     * </ul>
     *
     * @see FileAPITestTemplate#initBobBasicFileStructure
     */
    protected void initTemplate() throws Exception {
        _clearStorageDirectory();
        initDrive();
        initUsersToDB();
        initBobBasicFileStructure();
    }

    protected void _clearStorageDirectory() {
        var path = _getStoragePath();
        var parentDir = path.toFile();
        var filesInDir = parentDir.listFiles();

        if (!parentDir.exists() || !parentDir.isDirectory() || filesInDir == null) {
            throw new IllegalStateException("Path: " + path + " does not exist or is not directory");
        }

        for (var file : filesInDir) {
            var deleted = file.delete();
            if (!deleted) {
                throw new IllegalStateException();
            }
        }
    }

    protected boolean _fileExist(String fileId) {
        var storagePath = Paths.get(_getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected boolean _fileExist(UUID fileId) {
        var storagePath = Paths.get(_getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected byte[] _getFileContent(UUID fileId) {
        var storagePath = Paths.get(_getStoragePath() + "/" + fileId);
        var file = storagePath.toFile();

        try {
            var inputStream = new FileInputStream(file);
            return inputStream.readAllBytes();

        } catch (Exception ex) {
            throw new IllegalTestStateException(ex);
        }
    }

    protected boolean _fileContentEqual(UUID fileId, byte[] fileContent) {
        var content = _getFileContent(fileId);

        if (content.length != fileContent.length) {
            return false;
        }

        for (int i = 0; i < fileContent.length; i++) {
            if (content[i] != fileContent[i]) {
                return false;
            }
        }

        return true;
    }

    protected boolean _textFileContentEqual(UUID fileId, String fileContent) {
        var content = _getFileContent(fileId);
        var fileContentStr = new String(content);

        return fileContentStr.equals(fileContent);
    }

    protected boolean _fileStorageEmpty() {
        var storagePath = _getStoragePath();
        var files = storagePath.toFile().listFiles();

        assert files != null;
        return files.length == 0;
    }

}