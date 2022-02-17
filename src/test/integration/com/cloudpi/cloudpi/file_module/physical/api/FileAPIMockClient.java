package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPIMockClient;
import com.cloudpi.cloudpi.utils.controller_tests.FetchUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.UUID;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class FileAPIMockClient extends AbstractAPIMockClient {

    public FileAPIMockClient(MockMvc mockMvc, FetchUtils fetchUtils) {
        this.mockMvc = mockMvc;
        this.fetchUtils = fetchUtils;
    }

    //-------------uploadNewFile-------------
    public MockHttpServletRequestBuilder uploadNewFileRequest(String path, FileType type, MockMultipartFile file) {
        return multipart("/files/file")
                .file(file)
                .param("filepath", path)
                .param("fileType", type.name());
    }

    public FileInfoDTO uploadNewFile(String path, FileType type, MockMultipartFile file) throws Exception {
        var response = perform(
                uploadNewFileRequest(path, type, file)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performUploadNewFile(String path, FileType type, MockMultipartFile file) throws Exception {
        return perform(
                uploadNewFileRequest(path, type, file)
        );
    }

    public ResultActions performUploadNewFile(String path, FileType type, MockMultipartFile file, String asUsername) throws Exception {
        return perform(
                uploadNewFileRequest(path, type, file),
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
