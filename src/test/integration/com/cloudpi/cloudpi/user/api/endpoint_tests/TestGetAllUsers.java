package com.cloudpi.cloudpi.user.api.endpoint_tests;

import com.cloudpi.cloudpi.user.api.UserAPITestTemplate;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
class TestGetAllUsers extends UserAPITestTemplate {

    @BeforeEach
    void setPutUsers() throws Exception {
        addUsersToDB();
    }

    @Test
    @WithUser
    void should_return_all_users() throws Exception {
        var result = mockMvc.perform(
               get(apiAddress)
        ).andExpect(status().is2xxSuccessful()
        ).andReturn();

        var body = Arrays.stream(MockMvcUtils.getBody(result, UserIdDTO[].class)).toList();

        assert body.size() == createUserRequests.size() + 1;
        assert body.stream().anyMatch(u -> Objects.equals(u.getUsername(), "admin"));
        for(var userRequest : createUserRequests) {
            assert body.stream()
                    .anyMatch(u -> Objects.equals(u.getUsername(), userRequest.username()));
        }

    }

    @Test
    void should_return_401_to_non_authenticated_user() throws Exception {
        mockMvc.perform(
                get(apiAddress)
        ).andExpect(status().is(403));
    }

}
