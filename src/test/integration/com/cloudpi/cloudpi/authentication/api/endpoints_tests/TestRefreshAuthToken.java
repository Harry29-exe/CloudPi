package com.cloudpi.cloudpi.authentication.api.endpoints_tests;

import com.cloudpi.cloudpi.authentication.api.AuthenticationApiTestTemplate;
import com.cloudpi.cloudpi.utils.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestRefreshAuthToken extends AuthenticationApiTestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initDB();
    }

    @Test
    void should_return_new_auth_token() throws Exception {
        //given
        var user = userRequests.get(0);
        var tokens = fetchLoginAndGetTokens(
                user.username(),
                user.password());

        //when
        var response = fetchRefreshAuthToken(tokens.refreshToken)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        var authToken = response.getHeader("Authorization");
        assert authToken != null && !authToken.isBlank();

    }

}
