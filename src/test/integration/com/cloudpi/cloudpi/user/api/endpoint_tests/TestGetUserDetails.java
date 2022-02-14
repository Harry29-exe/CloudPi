package com.cloudpi.cloudpi.user.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBody;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestGetUserDetails extends UserAPITestTemplate {

    @BeforeEach
    void initUsersInDB() throws Exception {
        initTemplate();
    }



    @Test
    @WithUser(authorities = Role.admin)
    void should_return_details_of_2_users() throws Exception {
        //given
        var bobUsername = "bob";
        var aliceUsername = "Alice";


        //when
        var bobResponse = userAPI.performGetUserDetails(bobUsername)
                .andExpect(status().is(200))
                .andReturn();
        var aliceResponse = userAPI.performGetUserDetails(aliceUsername)
                .andExpect(status().is(200))
                .andReturn();

        //then
        var bobDetails = getBody(bobResponse, UserDetailsDTO.class);
        var aliceDetails = getBody(aliceResponse, UserDetailsDTO.class);

        assert bobDetails.getUsername().equals(bobUsername);
        assert aliceDetails.getUsername().equals(aliceUsername);
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_users_own_details() throws Exception {
        //given
        var bobUsername = "bob";


        //when
        var bobResponse = userAPI.performGetUserDetails(bobUsername)
                .andExpect(status().is(200))
                .andReturn();

        //then
        var bobDetails = getBody(bobResponse, UserDetailsDTO.class);

        assert bobDetails.getUsername().equals(bobUsername);
    }

    @Test
    void should_return_401_when_accessing_other_user_details() throws Exception {
        //given
        var username = "Alice";
        //when

        userAPI.performGetUserDetails(username)
                .andExpect(status().is(403));

    }

}
