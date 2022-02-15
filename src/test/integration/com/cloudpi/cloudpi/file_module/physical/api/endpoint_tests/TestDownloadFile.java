package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestDownloadFile extends FileAPITestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearDir() throws Exception {
        clearStorageDirectory();
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_download_save_file() throws Exception {
        //given
        var fileInfo = uploadDefaultFileAsBob();

        //when
        var fileResponse = mockMvc.perform(
                        fileAPI.downloadFileReqBuilder(fileInfo.getPubId()))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        //then
        assert fileResponse.getContentAsString()
                .equals(fileAPI.textfileContent);

    }

    @Test
    @WithUser(username = "Alice", authorities = Role.user)
    void should_return_403_when_user_has_no_permission() throws Exception {
        //given
        var fileInfo = uploadDefaultFileAsBob();

        //when
//        fileAPI.
        //todo
    }


    protected FileInfoDTO uploadDefaultFileAsBob() throws Exception {
        var response = fileAPI.uploadTextfileAs("bob")
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        return getBody(response, FileInfoDTO.class);
    }


}
