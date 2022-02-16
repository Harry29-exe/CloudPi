package com.cloudpi.cloudpi.file_module.filesystem.api.endpoint_tests;

import com.cloudpi.cloudpi.file_module.filesystem.api.FilesystemTestTemplate;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBodyAsList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestFileSearch extends FilesystemTestTemplate {

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
        var response = filesystemAPI
                .performSearchInUserFiles(query)
                .andExpect(status().isOk())
                .andReturn();

        //then
        var responseBody = getBodyAsList(response, FileInfoDTO.class);
        assert responseBody.size() == 1;
        assert responseBody.get(0).getName().equals(query.getName());
    }

}
