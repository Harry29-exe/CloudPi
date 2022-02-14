package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.physical.api.FileAPIMockClient;
import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FilesystemInfoTestTemplate extends AbstractAPITestTemplate {

    @Value("${cloud-pi.storage.mock.save-files-dir}")
    private String storageMockPath;
    @Autowired
    protected FileAPIMockClient fileAPI;
    @Autowired
    protected FilesystemInfoAPIMockClient filesystemInfoAPI;
    protected List<FileRequestData> initFiles = new ArrayList<>();

    protected void initTemplate() throws Exception {
        addUsersToDB();

        addDrive();
        createInitFiles();
        addFilesForBob();
    }

    protected void addFilesForBob() throws Exception {
        for (var fileReq : initFiles) {
            fileAPI.performUploadNewFile(fileReq.multipartFile, fileReq.path, fileReq.type, "bob")
                    .andExpect(status().is2xxSuccessful());
        }
    }

    private void createInitFiles() throws Exception {
        initFiles = List.of(
                new FileRequestData(
                        "bob/img.jpg",
                        FileType.IMAGE,
                        fileAPI.readFileFromResources("./test_files/example-image.jpg")),
                new FileRequestData(
                        "bob/text.pdf",
                        FileType.UNDEFINED,
                        fileAPI.readFileFromResources("./test_files/text.pdf")
                ),
                new FileRequestData(
                        "bob/text.txt",
                        FileType.TEXT_FILE,
                        fileAPI.readFileFromResources("./test_files/text.txt")
                )
        );

    }

    protected void addDrive() throws Exception {
        var drive = new PostDriveRequest(getStoragePath().toString(), (long) Math.pow(10, 10));
        var authToken = MockMvcUtils.getAdminAuthToken(mockMvc);
        mockMvc.perform(
                post("/drive/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(drive))
                        .header("Authorization", "Bearer " + authToken)
        ).andExpect(status().is2xxSuccessful());
    }

    protected static class FileRequestData {
        public final String path;
        public final FileType type;
        public final MockMultipartFile multipartFile;

        public FileRequestData(String path, FileType type, MockMultipartFile multipartFile) {
            this.path = path;
            this.type = type;
            this.multipartFile = multipartFile;
        }
    }

    protected Path getStoragePath() {
        if (storageMockPath.startsWith("~")) {
            var path = System.getProperty("user.home") + storageMockPath.substring(1);
            return Paths.get(path);
        }

        return Paths.get(storageMockPath);
    }


}