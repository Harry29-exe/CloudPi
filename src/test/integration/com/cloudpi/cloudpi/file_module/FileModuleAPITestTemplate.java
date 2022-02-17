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

    protected ImmutableList<UploadFileParams> _bobFilesToUpload;

    protected List<CreateDirParams> _bobDirectoriesToUpload;

    /**
     * <h3>By default adds following file structure for user bob</h3>
     * bob<br/>
     * &emsp;dir1<br/>
     * &emsp;&emsp;dir11<br/>
     * &emsp;&emsp;&emsp;img.jpg<br/>
     * &emsp;&emsp;text.txt<br/>
     * &emsp;dir2<br/>
     * &emsp;hello world.txt<br/>
     * &emsp;text.pdf<br/>
     *
     * @see FileModuleAPITestTemplate#_bobFilesToUpload
     * @see FileModuleAPITestTemplate#_bobDirectoriesToUpload
     */
    protected void initBobBasicFileStructure() throws Exception {
        initBobDirList();
        initBobFileList();
        for (var dir : _bobDirectoriesToUpload) {
            filesystemAPI.performCreateDirectory(dir.dirPath, "bob")
                    .andExpect(status().is2xxSuccessful());
        }

        for (var file : _bobFilesToUpload) {
            fileAPI.performUploadNewFile(
                            file.filepath,
                            file.fileType,
                            file.file,
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

    protected void initBobFileList() {
        _bobDirectoriesToUpload = ImmutableList.of(
                new CreateDirParams("bob/dir1"),
                new CreateDirParams("bob/dir2"),
                new CreateDirParams("bob/dir1/dir11")
        );
    }

    protected void initBobDirList() {
        _bobFilesToUpload = ImmutableList.of(
                new UploadFileParams(
                        "bob/dir1/dir11/img.jpg",
                        FileType.IMAGE,
                        _loadImageFileExampleImageJpg()
                ),
                new UploadFileParams(
                        "bob/dir1/text.txt",
                        FileType.TEXT_FILE,
                        _loadTextFileTextTxt()
                ),
                new UploadFileParams(
                        "bob/hello world.txt",
                        FileType.TEXT_FILE,
                        new MockMultipartFile("file", "Hello world in txt".getBytes(StandardCharsets.UTF_8))
                ),
                new UploadFileParams(
                        "bob/text.pdf",
                        FileType.UNDEFINED,
                        _loadPdfFileTextPdf()
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

    protected MockMultipartFile _loadTextFileTextTxt() {
        return fileTestUtils.readFileFromResources("./test_files/text.txt");
    }

    protected MockMultipartFile _loadPdfFileTextPdf() {
        return fileTestUtils
                .readFileFromResources("./test_files/text.pdf");
    }

    protected MockMultipartFile _loadImageFileExampleImageJpg() {
        return fileTestUtils
                .readFileFromResources("./test_files/example-image.jpg");
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
