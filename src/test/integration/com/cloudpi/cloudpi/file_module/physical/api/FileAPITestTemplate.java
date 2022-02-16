package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.FileModuleAPITestTemplate;
import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class FileAPITestTemplate extends FileModuleAPITestTemplate {

    @Value("${cloud-pi.storage.mock.save-files-dir}")
    private String storageMockPath;


    protected final ObjectMapper jsonMapper = new JsonMapper();

    protected void initTemplate() throws Exception {
        clearStorageDirectory();
        addDrive();
        initUsersToDB();
    }

    protected void addDrive() throws Exception {
        var drive = new PostDriveRequest(_getStoragePath().toString(), (long) Math.pow(10, 10));
        var authToken = MockMvcUtils.getAdminAuthToken(mockMvc);
        mockMvc.perform(
                post("/drive/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(drive))
                        .header("Authorization", "Bearer " + authToken)
        ).andExpect(status().is2xxSuccessful());
    }

    protected void clearStorageDirectory() {
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

    protected boolean fileExist(String fileId) {
        var storagePath = Paths.get(_getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected boolean fileExist(UUID fileId) {
        var storagePath = Paths.get(_getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected boolean fileStorageEmpty() {
        var storagePath = _getStoragePath();
        var files = storagePath.toFile().listFiles();

        assert files != null;
        return files.length == 0;
    }

    protected Path _getStoragePath() {
        if (storageMockPath.startsWith("~")) {
            var path = System.getProperty("user.home") + storageMockPath.substring(1);
            return Paths.get(path);
        }

        return Paths.get(storageMockPath);
    }

}