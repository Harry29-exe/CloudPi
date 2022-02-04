package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = userRepo.findByUsername(username)
                .orElseThrow(ResourceNotExistException::new);
        return new User(entity.getUsername(), entity.getPassword(), List.of());
    }

    @Override
    public ImmutableList<UserIdDTO> getAllUsers() {
        var users = userRepo.findAll()
                .stream()
                .map(UserEntity::toUserIdDTO)
                .collect(ImmutableList.toImmutableList());
        return users;
    }

    @Override
    public ImmutableList<UserDetailsDTO> getUserDetails(List<String> username) {
        var users = userRepo.findAllByUsernameIn(username)
                .stream()
                .map(UserEntity::toUserDetailsDTO)
                .collect(ImmutableList.toImmutableList());
        return users;
    }

    @Override
    public void createNewUser(PostUserRequest user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        UserEntity userEntity = new UserEntity(user.username(), user.password(),
                new com.cloudpi.cloudpi.user.domain.entities.UserDetails(user.nickname(), user.email(), null),
                roles);
        userRepo.save(userEntity);
    }

    @Override
    public void updateUserDetails(String username, PatchUserRequest request) {
        var user = userRepo.findByUsername(username).orElseThrow();
        if(request.email() != null) {
            user.getUserDetails().setEmail(request.email());
        }
        if(request.nickname() != null) {
            user.getUserDetails().setNickname(request.nickname());
        }
        if(request.pathToProfilePicture() != null) {
            user.getUserDetails().setPathToProfilePicture(request.pathToProfilePicture());
        }

        userRepo.save(user);
    }

    @Override
    public void deleteUser(String username) {
        var user = userRepo.findByUsername(username).orElseThrow();
        userRepo.delete(user);
    }
}
