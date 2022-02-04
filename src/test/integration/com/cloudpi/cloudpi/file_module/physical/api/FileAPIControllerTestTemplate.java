package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.utils.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ControllerTest
class FileAPIControllerTestTemplate {

    @Autowired
    MockMvc mockMvc;

    void initializeDatabase() {

    }

}