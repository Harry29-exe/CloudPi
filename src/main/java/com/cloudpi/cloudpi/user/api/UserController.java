package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
// TODO lepsza obsluga wyjatkow, np przy tworzeniu uzytkownika przy walidacji
public class UserController implements UserAPI {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserIdDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public List<UserDetailsDTO> getUserDetails(List<String> usernames) {
        return userService.getUserDetails(usernames);
    }

    @Override
    public void createNewUser(PostUserRequest user) {
        userService.createNewUser(user);
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
