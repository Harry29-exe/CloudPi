package com.cloudpi.cloudpi.user.dto;

import com.cloudpi.cloudpi.config.security.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsDTO {
    private String username;
    private String email;
    private Boolean hasProfileImage;
    private String nickname;
    private String pubId;
    private Set<Role> roles;
}
