package com.cloudpi.cloudpi.utils.api_tests;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.utils.IllegalTestStateException;
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

    public static String getAuthToken(MockMvc mockMvc, String username, String password) {
        var objectMapper = new JsonMapper();
        var loginRequest = new LoginRequest(username, password);

        try {

            String body = objectMapper.writeValueAsString(loginRequest);

            var result = mockMvc.perform(
                            post("/login")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(body)
                    ).andExpect(status().isOk())
                    .andReturn();

            return result.getResponse().getHeader("Authorization");


        } catch (Exception ex) {
            throw new IllegalTestStateException(ex);
        }
    }

    public static String getAdminAuthToken(MockMvc mockMvc) {
        return getAuthToken(mockMvc, "admin", "P@ssword123");
    }

    public static <T> T getBody(MvcResult result, Class<T> tClass) {
        ObjectMapper objectMapper = new JsonMapper();

        try {
            return objectMapper.readValue(
                    result.getResponse().getContentAsByteArray(),
                    tClass
            );
        } catch (Exception ex) {
            throw new IllegalTestStateException(ex);
        }
    }

    public static <T> T getBody(MockHttpServletResponse response, Class<T> tClass) {
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        try {
            return objectMapper.readValue(
                    response.getContentAsByteArray(),
                    tClass
            );
        } catch (Exception ex) {
            throw new IllegalTestStateException(ex);
        }
    }

    public static <T> List<T> getBodyAsList(MvcResult result, Class<T> tClass) {
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        try {
            var array = (T[]) objectMapper.readValue(
                    result.getResponse().getContentAsByteArray(),
                    tClass.arrayType()
            );

            return Arrays.asList(array);
        } catch (Exception ex) {
            throw new IllegalTestStateException(ex);
        }
    }

}
