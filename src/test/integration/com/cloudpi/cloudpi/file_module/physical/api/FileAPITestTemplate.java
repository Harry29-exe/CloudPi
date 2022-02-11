package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class FileAPITestTemplate extends AbstractAPITestTemplate {

    @Value("${cloud-pi.storage.mock.save-files-dir}")
    private String storageMockPath;

    @Autowired
    protected FileAPIMockClient fileAPI;

    protected final ObjectMapper jsonMapper = new JsonMapper();

    protected void initTemplate() throws Exception {
        clearStorageDirectory();
        addDrive();
        addUsersToDB();
    }

    protected void addDrive() throws Exception {
        var drive = new PostDriveRequest(getStoragePath().toString(), (long) Math.pow(10, 10));
        var authToken = MockMvcUtils.getAdminAuthToken(mockMvc);
        mockMvc.perform(
                post("/drive/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(drive))
                        .header("Authorization", authToken)
        ).andExpect(status().is2xxSuccessful());
    }

    protected void clearStorageDirectory() {
        var path = getStoragePath();
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
        var storagePath = Paths.get(getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected boolean fileExist(UUID fileId) {
        var storagePath = Paths.get(getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected boolean fileStorageEmpty() {
        var storagePath = getStoragePath();
        var files = storagePath.toFile().listFiles();

        assert files != null;
        return files.length == 0;
    }

    protected Path getStoragePath() {
        if (storageMockPath.startsWith("~")) {
            var path = System.getProperty("user.home") + storageMockPath.substring(1);
            return Paths.get(path);
        }

        return Paths.get(storageMockPath);
    }

}