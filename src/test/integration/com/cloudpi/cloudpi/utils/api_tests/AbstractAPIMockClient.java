package com.cloudpi.cloudpi.utils.api_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class AbstractAPIMockClient {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected FetchUtils fetchUtils;
    @Autowired
    protected ObjectMapper mapper;

    protected ResultActions perform(MockHttpServletRequestBuilder request, String username) throws Exception {
        return fetchUtils.as(username, request);
    }

    protected ResultActions perform(MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }


}
