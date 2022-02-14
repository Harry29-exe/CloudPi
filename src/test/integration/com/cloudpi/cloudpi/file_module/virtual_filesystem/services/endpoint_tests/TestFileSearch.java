package com.cloudpi.cloudpi.file_module.virtual_filesystem.services.endpoint_tests;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FilesystemInfoTestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBodyAsList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestFileSearch extends FilesystemInfoTestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @Test
    @WithUser
    void should_return_text_file_info() throws Exception {
        //given
        var query = new FileQueryDTO(
                "text.txt",
                FileType.TEXT_FILE,
                null,
                null
        );

        //when
        var response = filesystemInfoAPI
                .performSearchInUserFiles(query)
                .andExpect(status().isOk())
                .andReturn();

        //then
        var responseBody = getBodyAsList(response, FileInfoDTO.class);
        assert responseBody.size() == 1;
        assert responseBody.get(0).getName().equals(query.getName());
    }

}
