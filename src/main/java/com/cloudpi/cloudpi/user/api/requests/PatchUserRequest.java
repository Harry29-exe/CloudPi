package com.cloudpi.cloudpi.user.api.requests;


import org.springframework.lang.Nullable;

import java.util.UUID;

public record PatchUserRequest(
        @Nullable String nickname,
        @Nullable String email,
        @Nullable UUID profilePicturePubId
) {
}
