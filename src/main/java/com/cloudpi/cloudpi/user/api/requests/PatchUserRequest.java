package com.cloudpi.cloudpi.user.api.requests;


import org.springframework.lang.Nullable;

public record PatchUserRequest(
        @Nullable String nickname,
        @Nullable String email,
        @Nullable String pathToProfilePicture
) {
}
