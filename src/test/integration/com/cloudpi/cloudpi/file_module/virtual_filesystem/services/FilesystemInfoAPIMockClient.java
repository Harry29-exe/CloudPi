package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPIMockClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBodyAsList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class FilesystemInfoAPIMockClient extends AbstractAPIMockClient {

    private final String apiAddr = "/filesystem/";

    //--------searchInUserFiles---------
    public MockHttpServletRequestBuilder searchInUserFilesRequest(FileQueryDTO requestBody) throws Exception {
        return post(apiAddr + "search")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON);
    }

    public List<FileInfoDTO> searchInUserFiles(FileQueryDTO requestBody) throws Exception {
        var response = mockMvc.perform(
                        searchInUserFilesRequest(requestBody)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, FileInfoDTO.class);
    }

    public ResultActions performSearchInUserFiles(FileQueryDTO requestBody) throws Exception {
        return perform(
                searchInUserFilesRequest(requestBody)
        );
    }

    public ResultActions performSearchInUserFiles(FileQueryDTO requestBody, String asUsername) throws Exception {
        return perform(
                searchInUserFilesRequest(requestBody),
                asUsername
        );
    }

}
