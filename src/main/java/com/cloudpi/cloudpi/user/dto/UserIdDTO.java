package com.cloudpi.cloudpi.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserIdDTO {
    private String username;
    private String pubId;
    private String nickname;
    private String pathToProfilePicture;

}
