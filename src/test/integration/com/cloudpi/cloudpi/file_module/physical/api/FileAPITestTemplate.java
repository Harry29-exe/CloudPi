package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.MockMvcUtils;
import com.cloudpi.cloudpi.utils.UserAPIUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class FileAPITestTemplate {

    @Autowired
    protected MockMvc mockMvc;
    @Value("${cloud-pi.storage.mock.save-files-dir}")
    private String storageMockPath;

    protected final String apiAddress = "/files/";
    protected final ObjectMapper jsonMapper = new JsonMapper();

    protected final ImmutableList<PostUserRequest> userRequestList = ImmutableList.of(
            new PostUserRequest(
                    "bob",
                    "bob",
                    null,
                    "P@ssword123"
            ),
            new PostUserRequest(
                    "Alice",
                    "Alice",
                    null,
                    "P@ssword321"
            )
    );

    protected void initTemplate() throws Exception {
        clearStorageDirectory();
        addDrive();
        addUsersToDB();
    }

    protected void addUsersToDB() throws Exception {
        var userApiUtils = new UserAPIUtils(mockMvc);

        for (var userRequest : userRequestList) {
            userApiUtils.addUserToDB(userRequest);
        }
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

    protected ResultActions fetchCreateFile(MockMultipartFile file, FileType fileType, String filePath) throws Exception {
        return mockMvc.perform(
                multipart(apiAddress + "file")
                        .file(file)
                        .requestAttr("fileType", fileType)
                        .requestAttr("filepath", filePath)

        );
    }

    protected boolean fileExist(String fileId) {
        var storagePath = Paths.get(getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected boolean fileExist(UUID fileId) {
        var storagePath = Paths.get(getStoragePath() + "/" + fileId);
        return storagePath.toFile().exists();
    }

    protected Path getStoragePath() {
        if (storageMockPath.startsWith("~")) {
            var path = System.getProperty("user.home") + storageMockPath.substring(1);
            return Paths.get(path);
        }

        return Paths.get(storageMockPath);
    }

}