package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.exception.user.UserNotExistException;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.FilesystemInfoService;
import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.domain.entities.UserDetailsEntity;
import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.utils.AppService;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AppService
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final FilesystemInfoService filesystemService;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, FilesystemInfoService filesystemService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.filesystemService = filesystemService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);
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
        if(username.size() != users.size()) {
            throw new UserNotExistException();
        }

        return users;
    }

    @Override
    public void createNewUser(PostUserRequest user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        String password = passwordEncoder.encode(user.password());

        UserEntity userEntity = new UserEntity(
                user.username(),
                password,
                new UserDetailsEntity(
                        user.nickname(),
                        user.email(),
                        null),
                roles);
        UserEntity createdUser = userRepo.saveAndFlush(userEntity);
        filesystemService.createRoot(createdUser.getId());
    }

    @Override
    public void updateUserDetails(String username, PatchUserRequest request) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);
        if(request.email() != null) {
            user.getUserDetailsEntity().setEmail(request.email());
        }
        if(request.nickname() != null) {
            user.getUserDetailsEntity().setNickname(request.nickname());
        }
        if(request.pathToProfilePicture() != null) {
            user.getUserDetailsEntity().setPathToProfilePicture(request.pathToProfilePicture());
        }

        userRepo.save(user);
    }

    @Override
    public void deleteUser(String username) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);
        userRepo.delete(user);
    }
}
