package com.cloudpi.cloudpi.user.dto;

import com.cloudpi.cloudpi.config.security.Role;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsDTO {
    private String username;
    private String email;
    private UUID profilePicturePubId;
    private String nickname;
    private String pubId;
    private Set<Role> roles;
}
