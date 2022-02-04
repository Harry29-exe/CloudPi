package com.cloudpi.cloudpi.user.api.requests;

import com.cloudpi.cloudpi.validation.password.app.AppPassword;
import com.cloudpi.cloudpi.validation.username.ValidUsername;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public record PostUserRequest(
        @NotBlank @ValidUsername String username,
        @NotBlank String nickname,
        @Nullable String email,
        @NotBlank @AppPassword String password
) {
}
