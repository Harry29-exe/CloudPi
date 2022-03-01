package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.config.security.IsAdminOrMod;
import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.api.requests.PatchPasswordRequest;
import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/user/")
@Tag(name = "User Management API", description =
        "API for various operations with users")
public interface UserAPI {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all users",
            description = " Returns all basic user objects with their usernames, account types and paths to profile pictures.")
    List<UserIdDTO> getAllUsers();


    //todo(add with image param)
    //fixme
    @PreAuthorize("hasAnyRole('" + Role.admin + "', '" + Role.moderator + "') OR " +
            "#username == authentication.name")
    @GetMapping(value = "{username}/details")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all details of user with provided username",
            description = "Returns all details of user whose username matches the one specified in the URI path",
            parameters = @Parameter(name = "usernames", description = "One or more usernames whose details you want to acquire"))
    UserDetailsDTO getUsersDetails(@PathVariable(name = "username") String username);


    @IsAdminOrMod
    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates user with given values",
            description = "Creates user with parameters specified in the request body",
            parameters = @Parameter(name = "user", description = "JSON object of users registration data"))
    void createNewUser(@RequestBody @Valid PostUserRequest user);


    @PreAuthorize("hasAnyRole('" + Role.admin + "', '" + Role.moderator + "') OR " +
            "#username == authentication.name")
    @PatchMapping("{username}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates user with provided username",
            description = "Updates details like username, email and path to profile picture of a" +
                    "user with specified username")
    void updateUserDetails(@PathVariable(name = "username") String username,
                           @RequestBody PatchUserRequest request);


    @PatchMapping("profile-image")
    void setProfileImage(@RequestBody MultipartFile file);


    @PatchMapping("password")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "updates user's password",
            description = "Takes current and new password, and set new password to currently logged user")
    void updateUserPassword(@RequestBody PatchPasswordRequest request);


    @PreAuthorize("hasAnyRole('" + Role.admin + "', '" + Role.moderator + "') OR " +
            "#username == authentication.name")
    @DeleteMapping("{username}")
    @Operation(summary = "deletes user with provided username",
            description = "Deletes user whose username matches the one specified in the path")
    void deleteUser(@PathVariable(name = "username") String username);



}
