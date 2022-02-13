package com.cloudpi.cloudpi.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserIdDTO {
    private String username;
    private String pubId;
    private String nickname;
    private UUID profilePicturePubId;

}
