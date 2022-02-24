package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.exception.user.UserNotExistException;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.filesystem.services.FilesystemService;
import com.cloudpi.cloudpi.file_module.physical.services.FileService;
import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.domain.UserDetailsEntity;
import com.cloudpi.cloudpi.user.domain.UserEntity;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.user.repositiories.UserRepo;
import com.cloudpi.cloudpi.user.service.dto.CreateUser;
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
    private final FilesystemService filesystemService;
    private final FileInfoRepo fileInfoRepo;
    private final FileService fileService;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, FilesystemService filesystemService, FileInfoRepo fileInfoRepo,
                           FileService fileService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.filesystemService = filesystemService;
        this.fileInfoRepo = fileInfoRepo;
        this.fileService = fileService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);
        return new User(entity.getUsername(), entity.getPassword(), entity.getRoles());
    }

    @Override
    public ImmutableList<UserIdDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserEntity::toUserIdDTO)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public ImmutableList<UserDetailsDTO> getUserDetails(List<String> username) {
        var users = userRepo.findAllByUsernameIn(username)
                .stream()
                .map(UserEntity::toUserDetailsDTO)
                .collect(ImmutableList.toImmutableList());
        if (username.size() != users.size()) {
            throw new UserNotExistException();
        }

        return users;
    }

    @Override
    public UserDetailsDTO getUserDetails(String username) {
        var user = userRepo.findByUsername(username)
                .orElseThrow();

        return user.toUserDetailsDTO();
    }

    @Override
    public void createNewUser(CreateUser user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        String password = passwordEncoder.encode(user.getPassword());

        UserEntity userEntity = new UserEntity(
                user.getUsername(),
                password,
                new UserDetailsEntity(
                        user.getNickname(),
                        user.getEmail(),
                        null),
                roles);
        UserEntity createdUser = userRepo.saveAndFlush(userEntity);
        filesystemService.createRoot(createdUser.getId());
    }

    @Override
    public void updateUserDetails(String username, PatchUserRequest request) {
        var user = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);
        if (request.email() != null) {
            user.getUserDetails().setEmail(request.email());
        }
        if (request.nickname() != null) {
            user.getUserDetails().setNickname(request.nickname());
        }
        if (request.profilePicturePubId() != null) {
            var file = fileInfoRepo.findByPubId(request.profilePicturePubId())
                    .orElseThrow(ResourceNotExistException::new);
            user.getUserDetails().setProfilePicture(file);
        }

        userRepo.save(user);
    }

    @Override
    public void deleteUser(String username) {
        for(var id : fileInfoRepo.getUsersFilesIds(username, FileType.DIRECTORY)) {
            fileService.delete(id);
        }
        var user = userRepo.findByUsername(username)
                .orElseThrow(UserNotExistException::new);
        userRepo.delete(user);
    }
}
