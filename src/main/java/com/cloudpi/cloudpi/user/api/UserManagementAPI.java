package com.cloudpi.cloudpi.user.api;

import com.cloudpi.cloudpi.user.api.requests.PatchUserRequest;
import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user-management/")
@Tag(name = "User Management API", description =
        "API for various operations with users")
public interface UserManagementAPI {

    @Operation(
            summary = "returns all users",
            description = """
                    Returns all basic user objects with their usernames, account types and paths to
                    profile pictures.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized request")
            }
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "users", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<UserIdDTO> getAllUsers();

    @Operation(
            summary = "returns all details of user with provided username",
            description = """
                    Returns all details of user whose username matches the one specified in the
                    URI path
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized request"),
                    @ApiResponse(responseCode = "404", description = "No user with provided username")
            }
    )
    //    @Secured(UserAPIAuthorities.GET_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/details/{usernames}")
    List<UserDetailsDTO> getUserDetails(@PathVariable(name = "usernames") List<String> username);


    @Operation(
            summary = "creates user with given values",
            description = "Creates user with parameters specified in the request body",
            parameters = @Parameter(name = "user", description = "JSON object of users registration data"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters in body"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized request")
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNewUser(@RequestBody @Valid PostUserRequest user);


    @Operation(
            summary = "updates user with provided username",
            description = """
                    Updates details like username, email and path to profile picture of a
                    user with specified username
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized request"),
                    @ApiResponse(responseCode = "404", description = "No user with provided username")
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("users/{username}")
    void updateUserDetails(@PathVariable(name = "username") String username,
                           @RequestBody PatchUserRequest request);


    @Operation(
            summary = "deletes user with provided username",
            description = "Deletes user whose username matches the one specified in the path",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "403", description = "Unauthorized request"),
                    @ApiResponse(responseCode = "404", description = "No user with provided username")
            }
    )
    @DeleteMapping("{username}/delete-now")
    void deleteUser(@PathVariable(name = "username") String username);

}
