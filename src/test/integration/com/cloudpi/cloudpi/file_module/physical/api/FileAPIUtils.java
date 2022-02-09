package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.AbstractAPITestTemplate.FetchUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

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

        return mockMvc.perform(
                multipart(uploadNewFileAddr())
                        .file(file)
                        .param(asUsername + "/text.txt")
                        .param("fileType", FileType.TEXT_FILE.name())
        );
    }

    public String uploadNewFileAddr() {
        return "/files/file";
    }

    public String uploadNewImageAddr(String imageName) {
        return "/files/image/" + imageName;
    }

//    public String

}
