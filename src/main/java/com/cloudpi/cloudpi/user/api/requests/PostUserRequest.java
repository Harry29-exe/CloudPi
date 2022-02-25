package com.cloudpi.cloudpi.user.api.requests;

import com.cloudpi.cloudpi.validation.password.app.AppPassword;
import com.cloudpi.cloudpi.validation.username.ValidUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

public record PostUserRequest(
        @Schema(description = "Can contain only ASCII digits and letter")
        @NotBlank @ValidUsername String username,

        @NotBlank String nickname,

        @Schema(description = "nullable")
        @Nullable String email,

        @Schema(description = "Must be correct app password")
        @NotBlank @AppPassword String password
) {
}
