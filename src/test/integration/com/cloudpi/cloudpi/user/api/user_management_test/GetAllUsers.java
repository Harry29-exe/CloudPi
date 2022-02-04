package com.cloudpi.cloudpi.user.api.user_management_test;

import com.cloudpi.cloudpi.user.api.UserManagementAPITestTemplate;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.utils.ControllerTest;
import com.cloudpi.cloudpi.utils.MockClient;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ControllerTest
class GetAllUsers extends UserManagementAPITestTemplate {

    @BeforeEach
    void setPutUsers() throws Exception {
        addUsersToDB();
    }

    @Test
    @WithUser
    void should_return_all_users() throws Exception {
       var result = mockMvc.perform(
               get(apiAddress)
       ).andReturn();

        var body = (List<UserIdDTO>) MockClient.getBody(result, List.class);

        assert body.size() == createUserRequests.size() + 1;
        assert body.stream().anyMatch(u -> Objects.equals(u.getUsername(), "admin"));
        for(var userRequest : createUserRequests) {
            assert body.stream()
                    .anyMatch(u -> Objects.equals(u.getUsername(), userRequest.username()));
        }

    }

}
