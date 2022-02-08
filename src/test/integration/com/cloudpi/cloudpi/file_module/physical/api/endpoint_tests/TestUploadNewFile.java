package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static com.cloudpi.cloudpi.utils.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestUploadNewFile extends FileAPITestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearStorage() throws Exception {
        clearStorageDirectory();
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_save_given_file() throws Exception {
        //given
//        var txtFile = getClass().getClassLoader().getResourceAsStream("test_files/text.txt");
        var file = new MockMultipartFile(
                "file",
                "text.txt",
                "text/plan",
                "Hello, World!".getBytes());

        //when
        var response = mockMvc.perform(
                        multipart(apiAddress + "file")
                                .file(file)
                                .param("filepath", "bob/text.txt")
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        var fileInfo = getBody(response, FileInfoDTO.class);


        //then
        assert fileExist(fileInfo.getPubId());

    }

}
