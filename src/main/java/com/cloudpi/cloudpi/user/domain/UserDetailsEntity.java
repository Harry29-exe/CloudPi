package com.cloudpi.cloudpi.user.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Lob
    private byte[] image;
    @Column(nullable = false)
    private boolean hasProfileImage = false;

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserDetailsEntity(@NonNull String nickname,
                             @Nullable String email,
                             @Nullable byte[] image) {
        this.nickname = nickname;
        this.email = email;
        if (image != null) {
            this.image = image;
            this.hasProfileImage = true;
        }
    }

    public String getNickname() {
        return nickname;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public boolean isHasProfileImage() {
        return hasProfileImage;
    }

    @Nullable
    public byte[] getImage() {
        if (hasProfileImage) {
            return this.image;
        }

        return null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    public void setImage(byte[] image) {
        this.image = image;
        this.hasProfileImage = true;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
