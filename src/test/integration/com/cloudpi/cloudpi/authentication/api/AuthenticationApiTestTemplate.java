package com.cloudpi.cloudpi.authentication.api;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.utils.UserAPIUtils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthenticationApiTestTemplate {

    protected final String apiAddress = "/";
    protected final String refreshCookieName = AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME;
    private final JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    protected MockMvc mockMvc;
    protected UserAPIUtils userAPIUtils;

    protected final ImmutableList<PostUserRequest> userRequests = ImmutableList.of(
            new PostUserRequest("bob", "bob", null, "P@ssword123"),
            new PostUserRequest("Alice", "Alice", null, "P@ssword321")
    );

    protected void initDB() throws Exception {
        userAPIUtils = new UserAPIUtils(mockMvc);
        for (PostUserRequest request : userRequests) {
            userAPIUtils.addUserToDB(request);
        }
    }

    protected ResultActions fetchLogin(String username, String password) throws Exception {
        var requestBody = jsonMapper.writeValueAsString(
                new LoginRequest(username, password)
        );

        return mockMvc.perform(
                post(apiAddress + "login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
    }

    protected AuthTokens fetchLoginAndGetTokens(String username, String password) throws Exception {
        return new AuthTokens(this.fetchLogin(username, password));
    }

    protected ResultActions fetchLogout(AuthTokens tokens) throws Exception {
        return mockMvc.perform(
                post(apiAddress + "logout")
        );
    }

    protected ResultActions fetchRefreshAuthToken(String refreshToken) throws Exception {
        return mockMvc.perform(
                post(apiAddress + "refresh/auth-token")
                        .cookie(new Cookie(refreshCookieName, refreshToken))
        );
    }

    protected ResultActions fetchRefreshRefreshToken(AuthTokens tokens) throws Exception {
        return mockMvc.perform(
                post(apiAddress + "refresh/refresh-token")
        );
    }

    protected static class AuthTokens {
        public final String authToken;
        public final String refreshToken;

        public AuthTokens(ResultActions mockResult) {
            this(mockResult.andReturn().getResponse());
        }

        public AuthTokens(MockHttpServletResponse response) {
            authToken = response.getHeader("Authorization");
            refreshToken = Arrays.stream(response.getCookies())
                    .filter(cookie ->
                            cookie.getName().equals(AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME))
                    .findFirst()
                    .orElseThrow()
                    .getValue();

            validate();
        }

        private void validate() {
            if (authToken == null || authToken.isBlank()) {
                throw new IllegalArgumentException("Auth token is blank or null");
            }
            if (refreshToken == null || refreshToken.isBlank()) {
                throw new IllegalArgumentException("Refresh token is blank or null");
            }
        }
    }


}