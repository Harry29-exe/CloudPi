package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPITestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ControllerTest
public class UserAPITestTemplate extends AbstractAPITestTemplate {
    @Autowired
    protected UserAPIMockClient userAPI;

    protected void addUsersToDB() throws Exception {
        for (var createRequest : userRequestList) {
            fetch.asAdmin(userAPI
                            .createNewUserRequest(createRequest))
                    .andExpect(status().is2xxSuccessful());
        }
    }

//    protected void addUserToDB(PostUserRequest userRequest, String authToken) throws Exception {
//        mockMvc.perform(
//                post(apiAddress + "new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(userRequest))
//                        .header("Authorization", authToken)
//        ).andExpect(status().is(201));
//    }
//
//    protected ResultActions fetchCreateNewUser(PostUserRequest request) throws Exception {
//        var bodyStr = mapper.writeValueAsString(request);
//
//        //when
//        return mockMvc.perform(
//                post(apiAddress + "new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(bodyStr)
//        );
//    }
//
//    protected ResultActions fetchGetAllUsers() throws Exception {
//        return mockMvc.perform(
//                get(apiAddress)
//        );
//    }
//
//    protected List<UserIdDTO> fetchGetAllUsersAndReturnBody() throws Exception {
//        return getBodyAsList(
//                fetchGetAllUsers()
//                        .andExpect(status().is2xxSuccessful())
//                        .andReturn(),
//                UserIdDTO.class
//        );
//    }
//
//    protected ResultActions fetchDeleteUser(String username) throws Exception {
//        return mockMvc.perform(
//                delete(apiAddress + username)
//        );
//    }
//
//    protected ResultActions fetchUpdateUser(
//            String username,
//            PatchUserRequest request
//    ) throws Exception {
//
//        return mockMvc.perform(
//                patch(apiAddress + username)
//                        .content(mapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//    }

}
