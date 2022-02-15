package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ControllerTest
public class TestDeleteFile extends FileAPITestTemplate {

    @BeforeEach
    void init() throws Exception {
        initTemplate();
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_delete_file() {
        //given
//        var fileInfo = fileAPIUtils.
    }

}
