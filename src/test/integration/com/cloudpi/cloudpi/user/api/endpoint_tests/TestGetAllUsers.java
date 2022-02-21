package com.cloudpi.cloudpi.user.api.endpoint_tests;

import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.utils.api_tests.APITest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@APITest
class TestGetAllUsers extends UserAPITestTemplate {

    @BeforeEach
    void setPutUsers() throws Exception {
        initTemplate();
    }

    @Test
    @WithUser
    void should_return_all_users() throws Exception {
        //given
        var userRequestList = this.userRequestList;
        //when
        var allUsers = userAPI.getAllUsers();

        assert allUsers.size() == userRequestList.size() + 1;
        assert allUsers.stream().anyMatch(u -> Objects.equals(u.getUsername(), "admin"));

        for (var userRequest : userRequestList) {
            assert allUsers.stream()
                    .anyMatch(u ->
                            u.getUsername().equals(userRequest.username())
                    );
        }

    }

    @Test
    void should_return_401_to_non_authenticated_user() throws Exception {
        userAPI.performGetAllUsers()
                .andExpect(status().is(403));
    }

}
