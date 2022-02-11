package com.cloudpi.cloudpi.user.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class TestDeleteUser extends UserAPITestTemplate {

    @BeforeEach
    void setUpUsers() throws Exception {
        initTemplate();
    }

    @Test
    @WithUser(authorities = Role.moderator)
    void should_delete_user_when_user_is_moderator() throws Exception {
        //given
        var username = "Alice";

        //when
        userAPI.deleteUser(username);

        //then
        var allUsers = userAPI.getAllUsers();

        assert allUsers.size() == 2;
        assert allUsers.stream()
                .noneMatch(user -> user.getUsername().equals(username));
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_delete_user_when_user_is_deleting_itself() throws Exception {
        //given
        var username = "bob";

        //when
        userAPI.deleteUser(username);

        //then
        var allUsers = userAPI.getAllUsers();

        assert allUsers.size() == 2;
        assert allUsers.stream()
                .noneMatch(u -> u.getUsername().equals(username));
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_return_403_when_regular_user_delete_other_user() throws Exception {
        //given
        var username = "Alice";

        //when
        userAPI.performDeleteUser(username)
                //then
                .andExpect(status().is(403));
    }

    @Test
    void should_return_403_when_unauthorized_user_request_delete() throws Exception {
        //given
        var username = "Alive";

        //when
        userAPI.performDeleteUser(username)
                //then
                .andExpect(status().is(403));
    }


}
