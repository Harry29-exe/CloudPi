package com.cloudpi.cloudpi.user.api.user_management_test;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.MockClient;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static com.cloudpi.cloudpi.utils.MockClient.getBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestGetUserDetails extends UserAPITestTemplate {

    @BeforeEach
    void initUsersInDB() throws Exception {
        this.addUsersToDB();
    }



    @Test
    @WithUser(authorities = Role.admin)
    void should_return_details_of_2_users() throws Exception {
        var usernames = createUserRequests.stream()
                .map(PostUserRequest::username)
                .toList();

        var result = mockMvc.perform(
                get(endpointAddress(usernames))
        ).andExpect(status().isOk()
        ).andReturn();

        var body = getBody(result);

        assert usernames.size() == body.size();
        for(var username : usernames) {
            assert body.stream().anyMatch(details ->
                    details.getUsername().equals(username)
            );
        }
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_users_own_details() throws Exception {
        //given
        var username = "bob";
        //when
        var result = mockMvc.perform(
                get(endpointAddress(username))
        ).andExpect(status().is2xxSuccessful()
        ).andReturn();

        var body = getBody(result);

        //then
        assert body.size() == 1;
        assert body.get(0).getUsername().equals(username);
    }

    @Test
    void should_return_401_when_accessing_other_user_details() throws Exception {
        //given
        var username = "Alice";
        //when

    }


    private String endpointAddress(List<String> usernames) {
        return apiAddress + "/" +
                String.join(",", usernames) + "/details";
    }

    private String endpointAddress(String... usernames) {
        return apiAddress + "/" +
                String.join(",", usernames) + "/details";
    }

    private List<UserDetailsDTO> getBody(MvcResult mvcResult) throws Exception {
        return Arrays.stream(
                MockClient.getBody(mvcResult, UserDetailsDTO[].class)
        ).toList();
    }

}
