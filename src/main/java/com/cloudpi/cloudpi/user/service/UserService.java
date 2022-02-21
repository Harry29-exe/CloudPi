package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.user.service.dto.CreateUser;
import com.google.common.collect.ImmutableList;

import java.util.List;

public interface UserService {

    ImmutableList<UserIdDTO> getAllUsers();

    ImmutableList<UserDetailsDTO> getUserDetails(List<String> username);

    UserDetailsDTO getUserDetails(String username);

    void createNewUser(CreateUser user);

    void updateUserDetails(String username, PatchUserRequest request);

    void deleteUser(String username);

}
