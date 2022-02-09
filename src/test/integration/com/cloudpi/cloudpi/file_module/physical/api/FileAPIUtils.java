package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.AbstractAPITestTemplate.FetchUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class FileAPIUtils {
    private final MockMvc mockMvc;
    private final FetchUtils fetchUtils;

    public final String textfileContent = "This is test textfile";

    public FileAPIUtils(MockMvc mockMvc, FetchUtils fetchUtils) {
        this.mockMvc = mockMvc;
        this.fetchUtils = fetchUtils;
    }

    public ResultActions uploadTextfile(String asUsername) throws Exception {
        var file = new MockMultipartFile(
                "file",
                "text.txt",
                null,
                textfileContent.getBytes(StandardCharsets.UTF_8));

        return fetchUtils.fetchAs(
                asUsername,
                multipart(uploadNewFileAddr())
                        .file(file)
                        .param("filepath", asUsername + "/text.txt")
                        .param("fileType", FileType.TEXT_FILE.name())
        );
    }

    public static String uploadNewFileAddr() {
        return "/files/file";
    }

    public static MockHttpServletRequestBuilder uploadNewFileBuilder() {
        return post(uploadNewFileAddr());
    }

    public static String uploadNewImageAddr(String imageName) {
        return "/files/image/" + imageName;
    }

    public static MockHttpServletRequestBuilder uploadNewImageBuilder(String imageName) {
        return post(uploadNewImageAddr(imageName));
    }

    public static String downloadFileAddr(UUID filePubId) {
        return "/files/file/" + filePubId;
    }

    public static MockHttpServletRequestBuilder downloadFileBuilder(UUID filePubId) {
        return get(downloadFileAddr(filePubId));
    }

    public static String deleteFileAddr(UUID filePubId) {
        return "/files/file/" + filePubId;
    }

    public static MockHttpServletRequestBuilder deleteFileBuilder(UUID filePubId) {
        return delete(deleteFileAddr(filePubId));
    }

}
