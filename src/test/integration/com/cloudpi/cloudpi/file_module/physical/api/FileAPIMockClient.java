package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.api_tests.AbstractAPIMockClient;
import com.cloudpi.cloudpi.utils.api_tests.FetchUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static com.cloudpi.cloudpi.utils.api_tests.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class FileAPIMockClient extends AbstractAPIMockClient {

    public FileAPIMockClient(MockMvc mockMvc, FetchUtils fetchUtils) {
        this.mockMvc = mockMvc;
        this.fetchUtils = fetchUtils;
    }

    private final String apiAddr = "/files/";

    //-------------uploadNewFile-------------
    public MockHttpServletRequestBuilder uploadNewFileRequest(String path, FileType type, MockMultipartFile file) {
        return multipart(apiAddr + "file")
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
        return multipart(apiAddr + "image/" + imageName);
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


    //--------downloadFile---------
    public MockHttpServletRequestBuilder downloadFileRequest(UUID filePubId) throws Exception {

        return get(apiAddr + "file/" + filePubId);
    }

    public Resource downloadFile(UUID filePubId) throws Exception {

        var response = perform(
                downloadFileRequest(filePubId)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return new ByteArrayResource(response
                .getResponse()
                .getContentAsByteArray()
        );
    }

    public ResultActions performDownloadFile(UUID filePubId) throws Exception {

        return perform(
                downloadFileRequest(filePubId)
        );
    }

    public ResultActions performDownloadFile(UUID filePubId, String asUsername) throws Exception {

        return perform(
                downloadFileRequest(filePubId),
                asUsername
        );
    }


    //--------compressAndDownloadDirectory---------
    public MockHttpServletRequestBuilder compressAndDownloadDirectoryRequest(
            String directoryId
    ) throws Exception {
        var requestBuilder = get(apiAddr + "directory/" + directoryId);

        return requestBuilder;
    }

    public Resource compressAndDownloadDirectory(
            String directoryId
    ) throws Exception {

        var response = perform(
                compressAndDownloadDirectoryRequest(directoryId)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return new ByteArrayResource(
                response
                        .getResponse()
                        .getContentAsByteArray()
        );
    }

    public ResultActions performCompressAndDownloadDirectory(
            String directoryId
    ) throws Exception {

        return perform(
                compressAndDownloadDirectoryRequest(directoryId)
        );
    }

    public ResultActions performCompressAndDownloadDirectory(
            String directoryId,
            String asUsername
    ) throws Exception {

        return perform(
                compressAndDownloadDirectoryRequest(directoryId),
                asUsername
        );
    }

    //--------deleteFile---------
    public MockHttpServletRequestBuilder deleteFileRequest(UUID filePubId) throws Exception {

        return delete(apiAddr + "file/" + filePubId);
    }

    public void deleteFile(UUID filePubId) throws Exception {

        var response = perform(
                deleteFileRequest(filePubId)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    public ResultActions performDeleteFile(UUID filePubId) throws Exception {

        return perform(
                deleteFileRequest(filePubId)
        );
    }

    public ResultActions performDeleteFile(UUID filePubId, String asUsername) throws Exception {

        return perform(
                deleteFileRequest(filePubId),
                asUsername
        );
    }


    //--------deleteFiles---------
    public MockHttpServletRequestBuilder deleteFilesRequest(List<UUID> fileIds) throws Exception {
        var requestBuilder = delete(apiAddr + "file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fileIds));

        return requestBuilder;
    }

    public void deleteFiles(List<UUID> fileIds) throws Exception {

        var response = perform(
                deleteFilesRequest(fileIds)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    public ResultActions performDeleteFiles(List<UUID> fileIds) throws Exception {

        return perform(
                deleteFilesRequest(fileIds)
        );
    }

    public ResultActions performDeleteFiles(List<UUID> fileIds, String asUsername) throws Exception {

        return perform(
                deleteFilesRequest(fileIds),
                asUsername
        );
    }

}
