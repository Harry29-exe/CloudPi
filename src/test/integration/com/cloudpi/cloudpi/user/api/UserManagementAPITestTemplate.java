package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.MockClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserManagementAPITestTemplate {
    protected final String apiAddress = "/user";

    @Autowired
    protected MockMvc mockMvc;
    protected JsonMapper jsonMapper = new JsonMapper();

    protected List<PostUserRequest> createUserRequests = List.of(
        new PostUserRequest(
                "bob",
                "Bob",
                null,
                "SuperP@ssword321"
        ),
        new PostUserRequest(
                "Alice",
                "Alice",
                null,
                "SuperP@ssword123"
        )
    );

    protected void addUsersToDB() throws Exception {
        var token = MockClient.getAdminAuthToken(mockMvc);
        for(var createRequest : createUserRequests) {
            addUserToDB(createRequest, token);
        }
    }

    protected void addUserToDB(PostUserRequest userRequest, String authToken) throws Exception {
        mockMvc.perform(
                post(apiAddress + "/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(userRequest))
                        .header("Authorization", authToken)
        ).andExpect(status().is(201));
    }

}
