package com.cloudpi.cloudpi.authentication.api.endpoints_tests;

import com.cloudpi.cloudpi.authentication.api.AuthenticationApiController;
import com.cloudpi.cloudpi.authentication.api.AuthenticationApiTestTemplate;
import com.cloudpi.cloudpi.utils.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestLogout extends AuthenticationApiTestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initDB();
    }

    @Test
    void should_send_cookie_that_deletes_refresh_cookie() throws Exception {
        //given
        var user = userRequests.get(0);
        var username = user.username();
        var password = user.password();
        var authTokens =
                fetchLoginAndGetTokens(username, password);

        //when
        var response = fetchLogout(authTokens)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        var logoutCookie = response.getCookie(AuthenticationApiController.REFRESH_TOKEN_COOKIE_NAME);
        assert logoutCookie != null;
        assert logoutCookie.getValue().isBlank();
    }

}
