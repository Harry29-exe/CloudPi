package com.cloudpi.cloudpi.user.dto;

import com.cloudpi.cloudpi.config.security.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsDTO {
    private String email;
    private String pathToProfilePicture;
    private String nickname;
    private String pubId;
    private Set<Role> roles;
}
