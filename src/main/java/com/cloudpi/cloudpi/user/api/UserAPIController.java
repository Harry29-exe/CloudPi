package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchPasswordRequest;
import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import com.cloudpi.cloudpi.user.service.UserService;
import com.cloudpi.cloudpi.user.service.dto.CreateUser;
import com.cloudpi.cloudpi.utils.CurrentRequestUtils;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
public class UserAPIController implements UserAPI {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserAPIController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
    public Resource downloadProfileImage(String username) {
        return userService.getProfileImg(username);
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
    public void setProfileImage(MultipartFile file) {
        var userAuth = CurrentRequestUtils.getAuth()
                .orElseThrow();

        userService.updateUserProfilePicture(userAuth.getName(), file);
    }

    @Override
    public void updateUserDetails(String username, PatchUserRequest request) {
        userService.updateUserDetails(username, request);
    }

    @Override
    public void updateUserPassword(PatchPasswordRequest request) {
        var auth = CurrentRequestUtils.getAuth()
                .orElseThrow();

        if (auth instanceof UsernamePasswordAuthenticationToken userAuth) {
            var encodedPassw = (String) userAuth.getCredentials();
            if (!passwordEncoder.matches(request.currentPassword(), encodedPassw)) {
                throw new BadCredentialsException("User: " + auth.getName() + " tried change password but current password was not correct");
            }

            userService.updateUserPassword(auth.getName(), request.newPassword());
        }
    }

    @Override
    public void deleteUser(String username) {
        userService.deleteUser(username);
    }
}
