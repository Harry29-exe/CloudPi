package com.cloudpi.cloudpi.user.api.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

public record PatchUserRequest(
        @Schema(description = "nullable")
        @Nullable String nickname,

        @Schema(description = "nullable")
        @Nullable String email
) {
}
