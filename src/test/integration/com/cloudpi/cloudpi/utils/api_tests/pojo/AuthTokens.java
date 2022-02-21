package com.cloudpi.cloudpi.utils.api_tests.pojo;

import com.cloudpi.cloudpi.authentication.api.AuthenticationApiController;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

public class AuthTokens {
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
