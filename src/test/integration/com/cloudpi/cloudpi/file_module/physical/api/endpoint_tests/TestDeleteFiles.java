package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void should_delete_single_file() throws Exception {

    }


}
