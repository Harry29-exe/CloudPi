package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/user/")
@Tag(name = "User Management API", description =
        "API for various operations with users")
public interface UserAPI {

    @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns all users",
            description = " Returns all basic user objects with their usernames, account types and paths to profile pictures.")
    List<UserIdDTO> getAllUsers();

    @GetMapping("{usernames}/details")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "returns all details of user with provided username",
            description = "Returns all details of user whose username matches the one specified in the URI path")
    List<UserDetailsDTO> getUserDetails(@PathVariable(name = "usernames") List<String> username);



    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates user with given values",
            description = "Creates user with parameters specified in the request body",
            parameters = @Parameter(name = "user", description = "JSON object of users registration data"))
    void createNewUser(@RequestBody @Valid PostUserRequest user);


    @PatchMapping("{username}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "updates user with provided username",
            description = "Updates details like username, email and path to profile picture of a"+
                    "user with specified username")
    void updateUserDetails(@PathVariable(name = "username") String username,
                           @RequestBody PatchUserRequest request);


    @Operation(summary = "deletes user with provided username",
            description = "Deletes user whose username matches the one specified in the path")
    @DeleteMapping("{username}")
    void deleteUser(@PathVariable(name = "username") String username);

}
