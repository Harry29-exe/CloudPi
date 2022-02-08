package com.cloudpi.cloudpi.utils;

import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.cloudpi.cloudpi.user.api.UserAPITestTemplate.apiAddress;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAPIUtils {
    private final ObjectMapper jsonMapper = new JsonMapper();
    private final MockMvc mockMvc;

    public UserAPIUtils(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public void addUserToDB(PostUserRequest userRequest) throws Exception {
        var authToken = MockClient.getAdminAuthToken(mockMvc);
        mockMvc.perform(
                post(apiAddress + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(userRequest))
                        .header("Authorization", authToken)
        ).andExpect(status().is(201));
    }

}
