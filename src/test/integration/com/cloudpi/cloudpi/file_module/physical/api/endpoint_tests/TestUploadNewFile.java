package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestUploadNewFile extends FileAPITestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearStorage() throws Exception {
        _clearStorageDirectory();
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_save_given_file() throws Exception {
        //given
        var file = _loadTextFileTextTxt();

        //when
        var response = fileAPI
                .performUploadNewFile("bob/text1.txt", FileType.TEXT_FILE, file)
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        var fileInfo = getBody(response, FileInfoDTO.class);


        //then
        assert _fileExist(fileInfo.getPubId());
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_409_when_file_in_path_already_exist() throws Exception {
        //given
        var file = _loadTextFileTextTxt();

        //when
        fileAPI.performUploadNewFile("bob/text.txt", FileType.TEXT_FILE, file)
                //then
                .andExpect(status().is(409));
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_409_when_not_enough_space() throws Exception {
        //todo
    }

    @Test
    @WithUser(username = "Alice", authorities = Role.user)
    void should_return_403_when_saving_without_permissions() throws Exception {
        //given
        var file = _loadTextFileTextTxt();

        //when
        fileAPI.performUploadNewFile("bob/text1.txt", FileType.TEXT_FILE, file)
                //then
                .andExpect(status().is(403));

        assert _fileStorageEmpty();
    }

    @Test
    @WithUser(username = "admin", authorities = Role.admin)
    void should_return_403_to_admin_without_permissions() throws Exception {
        //given
        var file = _loadTextFileTextTxt();

        //when
        fileAPI.performUploadNewFile("bob/text1.txt", FileType.TEXT_FILE, file)
                //then
                .andExpect(status().is(403));

        assert _fileStorageEmpty();
    }

}
