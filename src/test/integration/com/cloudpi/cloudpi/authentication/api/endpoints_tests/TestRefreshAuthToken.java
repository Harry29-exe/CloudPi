package com.cloudpi.cloudpi.authentication.api.endpoints_tests;

import com.cloudpi.cloudpi.authentication.api.AuthenticationApiTestTemplate;
import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestRefreshAuthToken extends AuthenticationApiTestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @Test
    void should_return_new_auth_token() throws Exception {
        //given
        var user = userRequestList.get(0);
        var tokens = authAPI.login(new LoginRequest(
                user.username(),
                user.password()
        ));

        //when
        var response = authAPI
                .performRefreshAuthToken("Bearer " + tokens.refreshToken)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        //then
        var authToken = response.getHeader("Authorization");
        assert authToken != null && !authToken.isBlank();

    }

}
