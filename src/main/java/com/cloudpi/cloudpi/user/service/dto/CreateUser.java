package com.cloudpi.cloudpi.user.service.dto;

import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Value
public class CreateUser {

    @NotBlank String username;
    @NotBlank String nickname;
    @Nullable
    String email;
    @NotBlank String password;

}
