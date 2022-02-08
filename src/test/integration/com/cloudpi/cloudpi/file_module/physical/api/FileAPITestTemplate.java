package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.ControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@ControllerTest
public class FileAPITestTemplate {

    @Autowired
    protected MockMvc mockMvc;
    @Value("${cloud-pi.storage.mock.save-files-dir}")
    protected String storageMockPath;

    protected final String apiAddress = "/files/";

    protected void clearStorageDirectory() {
        var path = Paths.get(storageMockPath);
        var parentDir = path.toFile();
        var filesInDir = parentDir.listFiles();

        if (!parentDir.exists() || !parentDir.isDirectory() || filesInDir == null) {
            throw new IllegalStateException();
        }

        for (var file : filesInDir) {
            var deleted = file.delete();
            if (!deleted) {
                throw new IllegalStateException();
            }
        }
    }

    protected ResultActions fetchCreateFile(MockMultipartFile file, FileType fileType, String filePath) throws Exception {
        return mockMvc.perform(
                multipart(apiAddress + "file")
                        .file(file)
                        .requestAttr("fileType", fileType)
                        .requestAttr("filepath", filePath)

        );
    }

    protected boolean fileExist(String fileId) {
        var storagePath = Paths.get(storageMockPath + "/" + fileId);
        return storagePath.toFile().exists();
    }

}