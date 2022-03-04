package com.cloudpi.cloudpi.user.domain;

import com.cloudpi.cloudpi.config.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class RoleEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Role role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public RoleEntity(UserEntity user, Role role) {
        this.role = role;
        this.user = user;
    }
}
