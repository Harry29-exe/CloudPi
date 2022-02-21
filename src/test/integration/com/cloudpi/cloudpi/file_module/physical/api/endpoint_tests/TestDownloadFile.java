package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.api_tests.APITest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.Arrays.compare;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@APITest
public class TestDownloadFile extends FileAPITestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearDir() throws Exception {
        _clearStorageDirectory();
    }


    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_download_save_file() throws Exception {
        //given
        var uploadedFile = _uploadTextTxtFileToBobAsBob();
        var filePubId = uploadedFile.getFirst().getPubId();

        //when
        var fileResponse = fileAPI
                .performDownloadFile(filePubId)
                .andExpect(status().is(200))
                .andReturn()
                .getResponse();

        //then
        assert compare(
                uploadedFile.getSecond().getBytes(),
                fileResponse.getContentAsByteArray()
        ) == 0;
    }


    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_404_when_downloading_non_existing_file() throws Exception {
        //given
        var nonExistingFilePubId = new UUID(0, 0);

        //when
        fileAPI.performDownloadFile(nonExistingFilePubId)
                //then
                .andExpect(status().is(404));
    }


    @Test
    @WithUser(username = "Alice", authorities = Role.user)
    void should_return_403_when_user_has_no_permission() throws Exception {
        //given
        var uploadedFile = _uploadTextTxtFileToBobAsBob();
        var filePubId = uploadedFile.getFirst().getPubId();

        //when
        fileAPI.performDownloadFile(filePubId, "Alice")
                //then
                .andExpect(status().is(403));
    }


    @Test
    @WithUser(username = "admin", authorities = {Role.admin, Role.moderator, Role.user})
    void should_return_403_when_admin_has_no_permission() throws Exception {
        //given
        var uploadedFile = _uploadTextTxtFileToBobAsBob();
        var filePubId = uploadedFile.getFirst().getPubId();

        //when
        fileAPI.performDownloadFile(filePubId, "admin")
                //then
                .andExpect(status().is(403));
    }


}
