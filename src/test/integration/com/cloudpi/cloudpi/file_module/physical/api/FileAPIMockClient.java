package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPIMockClient;
import com.cloudpi.cloudpi.utils.controller_tests.FetchUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class FileAPIMockClient extends AbstractAPIMockClient {

    public final String textfileContent = "This is test textfile";

    public FileAPIMockClient(MockMvc mockMvc, FetchUtils fetchUtils) {
        this.mockMvc = mockMvc;
        this.fetchUtils = fetchUtils;
    }


    public ResultActions uploadTextfileAs(String asUsername) throws Exception {
        var file = new MockMultipartFile(
                "file",
                "text.txt",
                null,
                textfileContent.getBytes(StandardCharsets.UTF_8));

        return fetchUtils.as(
                asUsername,
                uploadNewFileRequest(file, asUsername + "/text.txt", FileType.TEXT_FILE)
        );
    }

    public ResultActions uploadTextfileTo(String path) throws Exception {
        var file = new MockMultipartFile(
                "file",
                "text.txt",
                null,
                textfileContent.getBytes(StandardCharsets.UTF_8));

        return mockMvc.perform(
                uploadNewFileRequest(file, path, FileType.TEXT_FILE)
        );
    }

    //-------------uploadNewFile-------------
    public MockHttpServletRequestBuilder uploadNewFileRequest(MockMultipartFile file, String path, FileType type) {
        return multipart("/files/file")
                .file(file)
                .param("filepath", path)
                .param("fileType", type.name());
    }

    public FileInfoDTO uploadNewFile(MockMultipartFile file, String path, FileType type) throws Exception {
        var response = perform(
                uploadNewFileRequest(file, path, type)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performUploadNewFile(MockMultipartFile file, String path, FileType type) throws Exception {
        return perform(
                uploadNewFileRequest(file, path, type)
        );
    }

    public ResultActions performUploadNewFile(MockMultipartFile file, String path, FileType type, String asUsername) throws Exception {
        return perform(
                uploadNewFileRequest(file, path, type),
                asUsername
        );
    }


    //-------------uploadNewImage-------------
    public MockMultipartHttpServletRequestBuilder uploadNewImageRequest(MockMultipartFile image, String imageName) {
        return multipart("/files/image/" + imageName);
    }

    public FileInfoDTO uploadNewImage(MockMultipartFile image, String imageName) throws Exception {
        var response = perform(
                uploadNewImageRequest(image, imageName)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performUploadNewImage(MockMultipartFile image, String imageName) throws Exception {
        return perform(
                uploadNewImageRequest(image, imageName)
        );
    }

    public ResultActions performUploadNewImage(MockMultipartFile image, String imageName, String asUsername) throws Exception {
        return perform(
                uploadNewImageRequest(image, imageName),
                asUsername
        );
    }


    //-------------downloadFile-------------
    public MockHttpServletRequestBuilder downloadFileReqBuilder(UUID filePubId) {
        return get("/files/file/" + filePubId);
    }

    //-------------deleteFile-------------
    public MockHttpServletRequestBuilder deleteFileReqBuilder(UUID filePubId) {
        return delete("/files/file/" + filePubId);
    }

}
