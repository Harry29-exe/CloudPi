package com.cloudpi.cloudpi.user.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Table(name = "user_details")
@Entity
@NoArgsConstructor
public class UserDetailsEntity {

    @Column(nullable = false)
    private @NotNull String nickname;
    /**
     * optional: useful for changing, getting info about
     * account being set to be deleted
     */
    @Column
    private String email;
    @Column
    private String pathToProfilePicture;
    @Id
    @Column(name = "user_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserDetailsEntity(@NonNull String nickname,
                             String email,
                             String pathToProfilePicture) {
        this.nickname = nickname;
        this.email = email;
        this.pathToProfilePicture = pathToProfilePicture;
    }

}