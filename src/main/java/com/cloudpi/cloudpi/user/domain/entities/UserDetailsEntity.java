package com.cloudpi.cloudpi.user.domain.entities;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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
    private @Nullable
    String email;

    @OneToOne
    @JoinColumn
    private @Nullable
    FileInfo profilePicture;

    @Id
    @Column(name = "user_id")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserDetailsEntity(@NonNull String nickname,
                             @Nullable String email,
                             @Nullable FileInfo profilePicture) {
        this.nickname = nickname;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public @Nullable
    UUID getProfilePicturePubId() {
        return profilePicture != null ?
                profilePicture.getPubId() :
                null;
    }

}
