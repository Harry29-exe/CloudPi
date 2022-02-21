package com.cloudpi.cloudpi.utils.api_tests;

import com.cloudpi.cloudpi.user.api.UserAPIMockClient;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractAPITestTemplate {

    @Autowired
    protected List<PostUserRequest> userRequestList;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    protected FetchUtils fetch;
    @Autowired
    protected UserAPIMockClient userAPI;


    protected void initUsersToDB() throws Exception {
        for (var userRequest : userRequestList) {
            userAPI.performCreateNewUser(userRequest, "admin")
                    .andExpect(status().is2xxSuccessful());
        }
    }

    protected abstract void initTemplate() throws Exception;

    @ComponentScan
    @Configuration
    public class TestConfig {

    }


}
