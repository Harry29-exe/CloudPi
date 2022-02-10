package com.cloudpi.cloudpi.utils.controller_tests;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.utils.controller_tests.pojo.AuthTokens;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class FetchUtils {

    private final Map<String, AuthTokens> authTokenMap = new HashMap<>();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private List<PostUserRequest> userRequestList;
    private final ObjectMapper mapper = new JsonMapper();

    public ResultActions as(String username, MockHttpServletRequestBuilder request) throws Exception {
        if (!authTokenMap.containsKey(username)) {
            fetchAuthToken(username);
        }

        return mockMvc.perform(
                request.header("Authorization", authTokenMap.get(username).authToken)
        );
    }

    public ResultActions asAndExpect2xx(
            String username,
            MockHttpServletRequestBuilder request
    ) throws Exception {

        return as(username, request)
                .andExpect(status().is2xxSuccessful());
    }

    public ResultActions asAdmin(MockHttpServletRequestBuilder request) throws Exception {
        return this.as("admin", request);
    }

    protected ResultActions fetchAsAdminAndExpect2xx(
            MockHttpServletRequestBuilder request
    ) throws Exception {
        return this.asAndExpect2xx("admin", request);
    }


    public void fetchAuthToken(String username) throws Exception {
        LoginRequest requestBody;

        if (username.equals("admin")) {
            requestBody = new LoginRequest("admin", "P@ssword123");

        } else {
            var user = userRequestList.stream()
                    .filter(u -> u.username().equals(username))
                    .findFirst()
                    .orElseThrow();

            requestBody = new LoginRequest(username, user.password());
        }


        var response = mockMvc.perform(
                        post("/login")
                                .content(mapper.writeValueAsString(requestBody))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        authTokenMap.put(username, new AuthTokens(response));
    }
}
