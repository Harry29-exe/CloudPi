package com.cloudpi.cloudpi.file_module.physical.api.authentication_api_tests;

import com.cloudpi.cloudpi.authentication.api.AuthenticationApiController;
import com.cloudpi.cloudpi.authentication.api.AuthenticationApiTestTemplate;
import com.cloudpi.cloudpi.utils.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestLogin extends AuthenticationApiTestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        this.initDB();
    }

    @Test
    void should_return_auth_tokens_for_admin() throws Exception {
        //given
        var username = "admin";
        var password = "P@ssword123";

        //when
        var response = fetchLogin(username, password)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        validateTokens(response);
    }


    @Test
    void should_return_auth_tokens_for_user() throws Exception {
        //given
        var user = this.userRequests.get(0);
        var username = user.username();
        var password = user.password();

        //when
        var response = fetchLogin(username, password)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        validateTokens(response);
    }

    @Test
    void should_return_403_when_given_bad_password() throws Exception {
        //given
        var user = this.userRequests.get(0);
        var username = user.username();
        var password = "bad_password";

        //when
        fetchLogin(username, password)
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
