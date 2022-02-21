package com.cloudpi.cloudpi.authentication.api;

import com.cloudpi.cloudpi.user.api.UserAPIMockClient;
import com.cloudpi.cloudpi.utils.api_tests.AbstractAPITestTemplate;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationApiTestTemplate extends AbstractAPITestTemplate {

    protected final String refreshCookieName = AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME;
    private final JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    protected UserAPIMockClient userAPI;
    @Autowired
    protected AuthenticationAPIMockClient authAPI;

    protected void initTemplate() throws Exception {
        initUsersToDB();
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