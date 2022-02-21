package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.api_tests.APITest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

@APITest
public class TestGetFileInfoByPath extends FileAPITestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @AfterAll
    void cleanUp() {
        _clearStorageDirectory();
    }


}
