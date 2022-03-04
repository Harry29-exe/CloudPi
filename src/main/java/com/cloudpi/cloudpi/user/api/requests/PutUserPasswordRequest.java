package com.cloudpi.cloudpi.user.api.requests;

public record PutUserPasswordRequest(
        String username,
        String newPassword
) {
}
