package com.cloudpi.cloudpi.user.domain.entities;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.FilesystemRootInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private UUID pubId = UUID.randomUUID();
    /**
     * For sending to other users in order to give opportunity
     * to share file with specific user
     */
    @Column(nullable = false, unique = true, updatable = false)
    private @NotBlank String username;
    @Column(nullable = false)
    private @NotBlank String password;
    @Column(nullable = false)
    private @NotNull Boolean locked = false;
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private @NotNull UserDetails userDetails;
    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FilesystemRootInfo userDrive;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public UserEntity(@NonNull String username,
                      @NonNull String password,
                      @NonNull UserDetails userDetails,
                      Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
        this.userDetails.setUser(this);
        this.roles = roles;
    }

}