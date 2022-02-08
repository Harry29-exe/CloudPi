package com.cloudpi.cloudpi.user.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.cloudpi.cloudpi.utils.MockClient.getBodyAsList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestCreateNewUser extends UserAPITestTemplate {

    private final String endpointAddress = apiAddress + "/new";
    private final PostUserRequest defaultRequestBody = new PostUserRequest(
            "newUser",
            "newUser",
            null,
            "123"
    );

    @BeforeEach
    void initUsersInDB() throws Exception {
        this.addUsersToDB();
    }

    @Test
    @WithUser(username = "admin", authorities = Role.admin)
    void should_add_create_new_user_when_user_have_admin_role() throws Exception {
        //given
        var username = defaultRequestBody.username();

        //when
        fetchCreateNewUser(defaultRequestBody)
                .andExpect(status().is(201));


        //then
        var usersResult = mockMvc.perform(
                get(apiAddress)
        ).andExpect(status().is(200)
        ).andReturn();

        var allUsers = getBodyAsList(usersResult, UserIdDTO.class);
        assert allUsers.size() == 4;
        assert allUsers.stream()
                .anyMatch(user ->
                        user.getUsername().equals(username)
                );
    }

    @Test
    @WithUser(username = "admin", authorities = Role.moderator)
    void should_create_new_user_when_user_have_mod_role() throws Exception {
        //given
        var username = defaultRequestBody.username();

        //when
        fetchCreateNewUser(defaultRequestBody)
                .andExpect(status().is(201));

        //then
        var result = fetchGetAllUsers()
                .andExpect(status().isOk())
                .andReturn();
        var allUsers = getBodyAsList(result, UserIdDTO.class);

        assert allUsers.size() == 4;
        assert allUsers.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Test
    @WithUser(authorities = Role.user)
    void should_return_401_to_unauthorized_user() throws Exception {
        //given
        //when
        fetchCreateNewUser(defaultRequestBody)
                //then
                .andExpect(status().is(403));

    }

    @Test
    void should_return_401_to_non_authenticated_user() throws Exception {
        //given
        //when
        fetchCreateNewUser(defaultRequestBody)
                //then
                .andExpect(status().is(403));

    }

}
