package com.cloudpi.cloudpi.utils;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;


import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MockClient {

    public static String getAuthToken(MockMvc mockMvc, String username, String password) throws Exception {
        var objectMapper = new JsonMapper();
        var loginRequest = new LoginRequest(username, password);
        String body = objectMapper.writeValueAsString(loginRequest);


        var result = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                ).andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getHeader("Authorization");
    }

    public static String getAdminAuthToken(MockMvc mockMvc) throws Exception {
        return getAuthToken(mockMvc, "admin", "P@ssword123");
    }

    public static <T> T getBody(MvcResult result, Class<T> tClass) throws Exception {
        ObjectMapper objectMapper = new JsonMapper();
        return objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                tClass
        );
    }

}
