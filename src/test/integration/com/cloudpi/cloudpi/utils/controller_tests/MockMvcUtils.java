package com.cloudpi.cloudpi.utils.controller_tests;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MockMvcUtils {

    public static String getAuthToken(MockMvc mockMvc, String username, String password) throws Exception {
        var objectMapper = new JsonMapper();
        var loginRequest = new LoginRequest(username, password);
        String body = objectMapper.writeValueAsString(loginRequest);


        var result = mockMvc.perform(
                        post("/login")
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

    public static <T> T getBody(MockHttpServletResponse response, Class<T> tClass) throws Exception {
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return objectMapper.readValue(
                response.getContentAsByteArray(),
                tClass
        );
    }

    public static <T> List<T> getBodyAsList(MvcResult result, Class<T> tClass) throws Exception {
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        var array = (T[]) objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                tClass.arrayType()
        );

        return Arrays.asList(array);
    }

}
