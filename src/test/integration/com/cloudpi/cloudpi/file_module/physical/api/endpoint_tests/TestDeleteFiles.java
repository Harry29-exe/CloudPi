package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

@ControllerTest
public class TestDeleteFiles extends FileAPITestTemplate {

    @BeforeEach
    void init() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearDir() throws Exception {
        clearStorageDirectory();
    }


}
