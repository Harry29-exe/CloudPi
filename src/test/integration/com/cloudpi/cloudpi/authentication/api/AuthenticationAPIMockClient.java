package com.cloudpi.cloudpi.authentication.api;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.utils.api_tests.AbstractAPIMockClient;
import com.cloudpi.cloudpi.utils.api_tests.pojo.AuthTokens;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class AuthenticationAPIMockClient extends AbstractAPIMockClient {

    private final String apiAddr = "/";

    //-----------login--------
    public MockHttpServletRequestBuilder loginRequest(LoginRequest requestBody) throws Exception {
        return post(apiAddr + "login")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON);
    }

    public AuthTokens login(LoginRequest requestBody) throws Exception {
        var response = perform(loginRequest(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse();

        return new AuthTokens(response);
    }

    public ResultActions performLogin(LoginRequest requestBody) throws Exception {
        return perform(
                loginRequest(requestBody)
        );
    }

    public ResultActions performLogin(LoginRequest requestBody, String asUsername) throws Exception {
        return perform(
                loginRequest(requestBody),
                asUsername
        );
    }

    //-----------login--------
    public MockHttpServletRequestBuilder refreshAuthTokenRequest(String refreshToken) throws Exception {
        return post(apiAddr + "refresh/auth-token")
                .cookie(new Cookie(
                        AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME,
                        refreshToken)
                );
    }

    public String refreshAuthToken(String refreshToken) throws Exception {
        return perform(
                refreshAuthTokenRequest(refreshToken)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getCookie(AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME)
                .getValue();
    }

    public ResultActions performRefreshAuthToken(String refreshToken) throws Exception {
        return perform(
                refreshAuthTokenRequest(refreshToken)
        );
    }

}
