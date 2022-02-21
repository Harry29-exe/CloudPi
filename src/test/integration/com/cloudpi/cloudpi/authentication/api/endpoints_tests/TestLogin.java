package com.cloudpi.cloudpi.authentication.api.endpoints_tests;

import com.cloudpi.cloudpi.authentication.api.AuthenticationApiController;
import com.cloudpi.cloudpi.authentication.api.AuthenticationApiTestTemplate;
import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.utils.api_tests.APITest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@APITest
public class TestLogin extends AuthenticationApiTestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        this.initTemplate();
    }

    @Test
    void should_return_auth_tokens_for_admin() throws Exception {
        //given
        var requestBody = new LoginRequest("admin", "P@ssword123");

        //when
        var response = authAPI.performLogin(requestBody)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        validateTokens(response);
    }


    @Test
    void should_return_auth_tokens_for_user() throws Exception {
        //given
        var user = this.userRequestList.get(0);
        var requestBody = new LoginRequest(user.username(), user.password());

        //when
        var response = authAPI.performLogin(requestBody)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        validateTokens(response);
    }

    @Test
    void should_return_403_when_given_bad_password() throws Exception {
        //given
        var user = this.userRequestList.get(0);
        var requestBody = new LoginRequest(
                user.username(),
                "B@d_password123"
        );

        //when
        authAPI.performLogin(requestBody)
                //then
                .andExpect(status().is(401));

    }

    void validateTokens(MockHttpServletResponse response) {
        var authToken = response.getHeader("Authorization");
        assert authToken != null && !authToken.isBlank();


        var refreshTokenCookie = Arrays.stream(response.getCookies())
                .filter(cookie -> cookie.getName().equals(AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME))
                .findFirst();
        assert refreshTokenCookie.isPresent();

        var refreshToken = refreshTokenCookie.get().getValue();
        assert refreshToken != null && !refreshToken.isBlank();

    }

}
