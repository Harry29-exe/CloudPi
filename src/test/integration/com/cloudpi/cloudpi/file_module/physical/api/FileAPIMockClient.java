package com.cloudpi.cloudpi.file_module.physical.api;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
                multipart(uploadNewFileAddr())
                        .file(file)
                        .param("filepath", asUsername + "/text.txt")
                        .param("fileType", FileType.TEXT_FILE.name())
        );
    }

    public ResultActions uploadTextfileTo(String path) throws Exception {
        var file = new MockMultipartFile(
                "file",
                "text.txt",
                null,
                textfileContent.getBytes(StandardCharsets.UTF_8));

        return mockMvc.perform(
                multipart(uploadNewFileAddr())
                        .file(file)
                        .param("filepath", path)
                        .param("fileType", FileType.TEXT_FILE.name())
        );
    }

//    public ResultActions downloadFile()

    public static String uploadNewFileAddr() {
        return "/files/file";
    }

    public static MockMultipartHttpServletRequestBuilder uploadNewFileReqBuilder() {
        return multipart(uploadNewFileAddr());
    }

    public static String uploadNewImageAddr(String imageName) {
        return "/files/image/" + imageName;
    }

    public static MockHttpServletRequestBuilder uploadNewImageReqBuilder(String imageName) {
        return post(uploadNewImageAddr(imageName));
    }

    public static String downloadFileAddr(UUID filePubId) {
        return "/files/file/" + filePubId;
    }

    public static MockHttpServletRequestBuilder downloadFileReqBuilder(UUID filePubId) {
        return get(downloadFileAddr(filePubId));
    }

    public static String deleteFileAddr(UUID filePubId) {
        return "/files/file/" + filePubId;
    }

    public static MockHttpServletRequestBuilder deleteFileReqBuilder(UUID filePubId) {
        return delete(deleteFileAddr(filePubId));
    }

}
