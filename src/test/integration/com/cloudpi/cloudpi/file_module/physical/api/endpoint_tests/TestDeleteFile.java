package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.api_tests.APITest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@APITest
public class TestDeleteFile extends FileAPITestTemplate {

    @BeforeEach
    void init() throws Exception {
        initTemplate();
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_delete_file() throws Exception {
        //given
        var uploadedFile = _uploadTextTxtFileToBobAsBob();
        var filePubId = uploadedFile.getFirst().getPubId();

        //when
    }

}
