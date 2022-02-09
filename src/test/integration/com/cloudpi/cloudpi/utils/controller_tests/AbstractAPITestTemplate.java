package com.cloudpi.cloudpi.utils.controller_tests;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractAPITestTemplate {

    protected List<PostUserRequest> userRequestList = ImmutableList.of(
            new PostUserRequest(
                    "bob",
                    "bob",
                    null,
                    "P@ssword123"
            ),
            new PostUserRequest(
                    "Alice",
                    "Alice",
                    null,
                    "P@ssword321"
            )
    );
    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper mapper = new JsonMapper();
    protected FetchUtils fetchUtils = new FetchUtils();


    public class FetchUtils {
        private final Map<String, AuthTokens> authTokenMap = new HashMap<>();

        public ResultActions fetchAs(String username, MockHttpServletRequestBuilder request) throws Exception {
            if (!authTokenMap.containsKey(username)) {
                fetchAuthToken(username);
            }

            return mockMvc.perform(
                    request.header("Authorization", authTokenMap.get(username).authToken)
            );
        }

        public ResultActions fetchAsAndExpect2xx(
                String username,
                MockHttpServletRequestBuilder request
        ) throws Exception {

            return fetchAs(username, request)
                    .andExpect(status().is2xxSuccessful());
        }

        public ResultActions fetchAsAdmin(MockHttpServletRequestBuilder request) throws Exception {
            return this.fetchAs("admin", request);
        }

        protected ResultActions fetchAsAdminAndExpect2xx(
                MockHttpServletRequestBuilder request
        ) throws Exception {
            return this.fetchAsAndExpect2xx("admin", request);
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

}
