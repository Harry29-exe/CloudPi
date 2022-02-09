package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.AfterAll;

@ControllerTest
public class TestDownloadFile extends FileAPITestTemplate {
    private final String endpointAddress = apiAddress + "file";

    //    @BeforeEach
    void setUp() throws Exception {
        initTemplate();

//        var
//        fetchAsAdminAndExpect2xx(
//                multipart(apiAddress + "/file")
//                        .file()
//        )
    }

    @AfterAll
    void clearDir() throws Exception {
        clearStorageDirectory();
    }

    //    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_download_save_file() {

    }


}
