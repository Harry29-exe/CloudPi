package com.cloudpi.cloudpi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserIdDTO {

    private String nickname;
    private String pubId;
    private String pathToProfilePicture;

}
