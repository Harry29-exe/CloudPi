package com.cloudpi.cloudpi.user.api.requests;

import com.cloudpi.cloudpi.validation.password.app.AppPassword;

public record PatchPasswordRequest(
        @AppPassword String currentPassword,
        @AppPassword String newPassword
) {
}
