package com.cloudpi.cloudpi.authentication.api;

import com.cloudpi.cloudpi.user.api.UserAPIMockClient;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPITestTemplate;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationApiTestTemplate extends AbstractAPITestTemplate {

    protected final String refreshCookieName = AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME;
    private final JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    protected UserAPIMockClient userAPI;
    @Autowired
    protected AuthenticationAPIMockClient authAPI;

    protected final ImmutableList<PostUserRequest> userRequests = ImmutableList.of(
            new PostUserRequest("bob", "bob", null, "P@ssword123"),
            new PostUserRequest("Alice", "Alice", null, "P@ssword321")
    );

    protected void initDB() throws Exception {
        for (PostUserRequest request : userRequests) {
            userAPI.performCreateNewUser(request, "admin")
                    .andExpect(status().is2xxSuccessful());
        }
    }

//    protected ResultActions fetchLogin(String username, String password) throws Exception {
//        var requestBody = jsonMapper.writeValueAsString(
//                new LoginRequest(username, password)
//        );
//
//        return mockMvc.perform(
//                post(apiAddress + "login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//        );
//    }
//
//    protected AuthTokens fetchLoginAndGetTokens(String username, String password) throws Exception {
//        return new AuthTokens(this.fetchLogin(username, password));
//    }
//
//    protected ResultActions fetchLogout(AuthTokens tokens) throws Exception {
//        return mockMvc.perform(
//                post(apiAddress + "logout")
//        );
//    }
//
//    protected ResultActions fetchRefreshAuthToken(String refreshToken) throws Exception {
//        return mockMvc.perform(
//                post(apiAddress + "refresh/auth-token")
//                        .cookie(new Cookie(refreshCookieName, refreshToken))
//        );
//    }
//
//    protected ResultActions fetchRefreshRefreshToken(AuthTokens tokens) throws Exception {
//        return mockMvc.perform(
//                post(apiAddress + "refresh/refresh-token")
//        );
//    }


}