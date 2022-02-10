package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPIMockClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static com.cloudpi.cloudpi.utils.controller_tests.MockMvcUtils.getBodyAsList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class UserAPIMockClient extends AbstractAPIMockClient {
    private final String apiAddr = "/user/";
    @Autowired
    private ObjectMapper mapper;

    //-----------------------getAllUsers------------------------------------
    public MockHttpServletRequestBuilder getAllUsersRequest() {
        return get(apiAddr);
    }

    public List<UserIdDTO> getAllUsers() throws Exception {
        var response = mockMvc.perform(
                        getAllUsersRequest())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, UserIdDTO.class);
    }

    public ResultActions performGetAllUsers() throws Exception {
        return perform(
                getAllUsersRequest()
        );
    }

    public ResultActions performGetAllUsers(String asUsername) throws Exception {
        return perform(
                getAllUsersRequest(),
                asUsername
        );
    }

    //-----------------------getUsersDetails------------------------------------
    public MockHttpServletRequestBuilder getUsersDetailsRequest(List<String> usernames) {
        if (usernames.size() == 0)
            throw new IllegalStateException();

        return get(apiAddr + String.join(",", usernames) + "/details");
    }

    public List<UserDetailsDTO> getUsersDetails(List<String> usernames) throws Exception {
        var response = mockMvc.perform(
                        getUsersDetailsRequest(usernames))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, UserDetailsDTO.class);
    }

    public ResultActions performGetUserDetails(List<String> usernames) throws Exception {
        return perform(
                getUsersDetailsRequest(usernames)
        );
    }

    public ResultActions performGetUserDetails(List<String> usernames, String asUsername) throws Exception {
        return perform(
                getUsersDetailsRequest(usernames),
                asUsername
        );
    }

    //-----------------------createNewUser------------------------------------
    public MockHttpServletRequestBuilder createNewUserRequest(PostUserRequest reqBody) throws Exception {
        return post(apiAddr + "new")
                .content(mapper.writeValueAsString(reqBody))
                .contentType(MediaType.APPLICATION_JSON);
    }

    public void createNewUser(PostUserRequest reqBody) throws Exception {
        perform(
                createNewUserRequest(reqBody)
        )
                .andExpect(status().is2xxSuccessful());
    }

    public ResultActions performCreateNewUser(PostUserRequest reqBody) throws Exception {
        return perform(
                createNewUserRequest(reqBody)
        );
    }

    public ResultActions performCreateNewUser(PostUserRequest reqBody, String asUsername) throws Exception {
        return perform(
                createNewUserRequest(reqBody),
                asUsername
        );
    }

    //-----------------------updateUserDetails------------------------------------
    public MockHttpServletRequestBuilder updateUserDetailsRequest(String username, PatchUserRequest reqBody) throws Exception {
        return patch(apiAddr + username)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqBody));
    }

    //-----------------------deleteUser------------------------------------
    public MockHttpServletRequestBuilder deleteUserRequest(String username) throws Exception {
        return delete(apiAddr + username);
    }

    public void deleteUser(String username) throws Exception {
        perform(
                deleteUserRequest(username)
        )
                .andExpect(status().is2xxSuccessful());
    }

    public ResultActions performDeleteUser(String username) throws Exception {
        return perform(
                deleteUserRequest(username)
        );
    }

    public ResultActions performDeleteUser(String username, String asUsername) throws Exception {
        return perform(
                deleteUserRequest(username),
                asUsername
        );
    }


}
