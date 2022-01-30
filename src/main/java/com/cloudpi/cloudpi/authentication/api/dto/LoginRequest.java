package com.cloudpi.cloudpi.authentication.api.dto;


import com.cloudpi.cloudpi.validation.password.app.AppPassword;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @AppPassword String password
) {
}
