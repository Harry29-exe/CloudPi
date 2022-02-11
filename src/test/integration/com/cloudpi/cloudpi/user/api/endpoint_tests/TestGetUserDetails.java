package com.cloudpi.cloudpi.user.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBodyAsList;
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
        var usernames = userRequestList.stream()
                .map(PostUserRequest::username)
                .toList();

        //when
        var response = userAPI.performGetUserDetails(usernames)
                .andExpect(status().is(200))
                .andReturn();

        //then
        var returnedUserDetails = getBodyAsList(response, UserDetailsDTO.class);

        assert usernames.size() == returnedUserDetails.size();
        for (var username : usernames) {
            assert returnedUserDetails
                    .stream()
                    .anyMatch(details ->
                            details.getUsername().equals(username)
                    );
        }
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_users_own_details() throws Exception {
        //given
        var username = List.of("bob");
        //when
        var result = userAPI
                .performGetUserDetails(username)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        var body = getBody(result);

        //then
        assert body.size() == 1;
        assert body.get(0).getUsername().equals(username.get(0));
    }

    @Test
    void should_return_401_when_accessing_other_user_details() throws Exception {
        //given
        var username = List.of("Alice");
        //when

        userAPI.performGetUserDetails(username)
                .andExpect(status().is(403));

    }

    private List<UserDetailsDTO> getBody(MvcResult mvcResult) throws Exception {
        return Arrays.stream(
                MockMvcUtils.getBody(mvcResult, UserDetailsDTO[].class)
        ).toList();
    }

}
