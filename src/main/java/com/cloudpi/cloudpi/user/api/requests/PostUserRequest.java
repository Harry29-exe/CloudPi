package com.cloudpi.cloudpi.user.api.requests;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public record PostUserRequest(
        @NotBlank String username,
        @NotBlank String nickname,
        @Nullable String email,
        @NotBlank String password
) {
}
