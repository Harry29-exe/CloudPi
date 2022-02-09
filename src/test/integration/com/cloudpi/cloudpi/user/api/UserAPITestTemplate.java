package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBodyAsList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ControllerTest
public class UserAPITestTemplate {
    public static final String apiAddress = "/user/";

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
        long time0 = System.nanoTime();
        var token = MockMvcUtils.getAdminAuthToken(mockMvc);
        System.out.println("\n\nTime: " + ((System.nanoTime() - time0) / 1_000_000) + "ms\n\n");
        for (var createRequest : createUserRequests) {
            addUserToDB(createRequest, token);
        }

        System.out.println("\n\nTime: " + ((System.nanoTime() - time0) / 1_000_000) + "ms\n\n");
    }

    protected void addUserToDB(PostUserRequest userRequest, String authToken) throws Exception {
        mockMvc.perform(
                post(apiAddress + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(userRequest))
                        .header("Authorization", authToken)
        ).andExpect(status().is(201));
    }

    protected ResultActions fetchCreateNewUser(PostUserRequest request) throws Exception {
        var bodyStr = this.jsonMapper.writeValueAsString(request);

        //when
        return mockMvc.perform(
                post(apiAddress + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyStr)
        );
    }

    protected ResultActions fetchGetAllUsers() throws Exception {
        return mockMvc.perform(
                get(apiAddress)
        );
    }

    protected List<UserIdDTO> fetchGetAllUsersAndReturnBody() throws Exception {
        return getBodyAsList(
                fetchGetAllUsers()
                        .andExpect(status().is2xxSuccessful())
                        .andReturn(),
                UserIdDTO.class
        );
    }

    protected ResultActions fetchDeleteUser(String username) throws Exception {
        return mockMvc.perform(
                delete(apiAddress + username)
        );
    }

    protected ResultActions fetchUpdateUser(
            String username,
            PatchUserRequest request
    ) throws Exception {

        return mockMvc.perform(
                patch(apiAddress + username)
                        .content(jsonMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

}
