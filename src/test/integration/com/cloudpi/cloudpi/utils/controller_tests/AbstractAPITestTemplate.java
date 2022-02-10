package com.cloudpi.cloudpi.utils.controller_tests;

import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

public abstract class AbstractAPITestTemplate {


    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper mapper = new JsonMapper();
    @Autowired
    protected FetchUtils fetch;
    @Autowired
    protected List<PostUserRequest> userRequestList;

    @ComponentScan
    @Configuration
    public class TestConfig {

    }


}
