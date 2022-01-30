package com.cloudpi.cloudpi.user.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "user_details")
@Entity
@NoArgsConstructor
public class UserDetails {

    public UserDetails(@NonNull String nickname,
                       String email,
                       String pathToProfilePicture) {
        this.nickname = nickname;
        this.email = email;
        this.pathToProfilePicture = pathToProfilePicture;
    }

    @Column(nullable = false)
    private @NonNull String nickname;

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

}
