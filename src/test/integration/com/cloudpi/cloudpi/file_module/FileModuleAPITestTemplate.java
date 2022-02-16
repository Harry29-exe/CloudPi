package com.cloudpi.cloudpi.file_module;

import com.cloudpi.cloudpi.file_module.filesystem.api.FilesystemAPIMockClient;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPIMockClient;
import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class FileModuleAPITestTemplate extends AbstractAPITestTemplate {

    @Autowired
    protected FileTestUtils fileTestUtils;
    @Autowired
    protected FileAPIMockClient fileAPI;
    @Autowired
    protected FilesystemAPIMockClient filesystemAPI;

    @Value("${cloud-pi.storage.mock.save-files-dir}")
    private String storageMockPath;

    protected List<UploadFileParams> _filesToUpload = ImmutableList.of(
            new UploadFileParams(
                    "bob/dir1/dir11/img.jpg",
                    FileType.IMAGE,
                    fileTestUtils.readFileFromResources("./test_files/example-image.jpg")),
            new UploadFileParams(
                    "bob/text.pdf",
                    FileType.UNDEFINED,
                    fileTestUtils.readFileFromResources("./test_files/text.pdf")
            ),
            new UploadFileParams(
                    "bob/dir1/text.txt",
                    FileType.TEXT_FILE,
                    fileTestUtils.readFileFromResources("./test_files/text.txt")
            ),
            new UploadFileParams(
                    "bob/hello world.txt",
                    FileType.TEXT_FILE,
                    new MockMultipartFile("file", "Hello world in txt".getBytes(StandardCharsets.UTF_8))
            )
    );

    protected List<CreateDirParams> _directoriesToUpload = ImmutableList.of(
            new CreateDirParams("bob/dir1"),
            new CreateDirParams("bob/dir2"),
            new CreateDirParams("bob/dir1/dir11")
    );

    protected void initBasicFileStructure() throws Exception {
        for (var dir : _directoriesToUpload) {
            filesystemAPI.performCreateDirectory(dir.dirPath, "bob")
                    .andExpect(status().is2xxSuccessful());
        }

        for (var file : _filesToUpload) {
            fileAPI.performUploadNewFile(
                            file.file,
                            file.filepath,
                            file.fileType,
                            "bob"
                    )
                    .andExpect(status().is2xxSuccessful());
        }
    }

    protected void initDrive() throws Exception {
        var drive = new PostDriveRequest(_getStoragePath().toString(), (long) Math.pow(10, 10));
        var authToken = MockMvcUtils.getAdminAuthToken(mockMvc);
        mockMvc.perform(
                post("/drive/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(drive))
                        .header("Authorization", "Bearer " + authToken)
        ).andExpect(status().is2xxSuccessful());
    }

    protected Path _getStoragePath() {
        if (storageMockPath.startsWith("~")) {
            var path = System.getProperty("user.home") + storageMockPath.substring(1);
            return Paths.get(path);
        }

        return Paths.get(storageMockPath);
    }

    @lombok.Value
    protected static class UploadFileParams {
        String filepath;
        FileType fileType;
        MockMultipartFile file;
    }

    @lombok.Value
    protected static class CreateDirParams {
        String dirPath;
    }

}
