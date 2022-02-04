package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.google.common.collect.ImmutableList;
import java.util.List;

public interface UserService {
    ImmutableList<UserIdDTO> getAllUsers();
    ImmutableList<UserDetailsDTO> getUserDetails(List<String> username);
    void createNewUser(PostUserRequest user);
    void updateUserDetails(String username, PatchUserRequest request);
    void deleteUser(String username);
}
