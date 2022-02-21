package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.user.service.UserService;
import com.cloudpi.cloudpi.user.service.dto.CreateUser;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAPIController implements UserAPI {
    private final UserService userService;

    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserIdDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public UserDetailsDTO getUsersDetails(String username) {
        return userService.getUserDetails(username);
    }

    @Override
    public void createNewUser(PostUserRequest user) {
        userService.createNewUser(new CreateUser(
                user.username(),
                user.nickname(),
                user.email(),
                user.password()
        ));
    }

    @Override
    public void updateUserDetails(String username, PatchUserRequest request) {
        userService.updateUserDetails(username, request);
    }

    @Override
    public void deleteUser(String username) {
        userService.deleteUser(username);
    }
}
