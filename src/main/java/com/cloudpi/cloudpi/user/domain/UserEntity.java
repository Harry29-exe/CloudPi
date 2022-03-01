package com.cloudpi.cloudpi.user.domain;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.filesystem.domain.FilesystemInfo;
import com.cloudpi.cloudpi.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi.user.dto.UserIdDTO;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private @NotNull UserDetailsEntity userDetails;

    @OneToOne(mappedBy = "owner",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private FilesystemInfo userDrive;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    //---------------Constructors---------------
    public UserEntity(@NonNull String username,
                      @NonNull String password,
                      @NonNull UserDetailsEntity userDetails,
                      Set<Role> roles) {

        this.username = username;
        this.password = password;
        this.userDetails = userDetails;
        this.userDetails.setUser(this);
        this.roles = roles.stream()
                .map(r -> new RoleEntity(this, r))
                .collect(Collectors.toSet());
    }

    //---------------Public---------------
    public void addRole(Role role) {
        var roleExist = roles.stream()
                .anyMatch(r -> r.getRole().equals(role));

        if(!roleExist) {
            roles.add(new RoleEntity(this, role));
        }
    }

    //---------------Mappers---------------
    public UserIdDTO toUserIdDTO() {
        return new UserIdDTO(
                username,
                pubId.toString(),
                userDetails.getNickname(),
                userDetails.isHasProfileImage());
    }

    public UserDetailsDTO toUserDetailsDTO() {
        return new UserDetailsDTO(
                username,
                userDetails.getEmail(),
                userDetails.isHasProfileImage(),
                userDetails.getNickname(),
                pubId.toString(),
                roles.stream()
                        .map(RoleEntity::getRole)
                        .collect(Collectors.toCollection(HashSet::new))
        );
    }

    //---------------Getters---------------
    public Long getId() {
        return id;
    }

    public UUID getPubId() {
        return pubId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getLocked() {
        return locked;
    }

    public UserDetailsEntity getUserDetails() {
        return userDetails;
    }

    public FilesystemInfo getUserDrive() {
        return userDrive;
    }

    public List<Role> getRoles() {
        return this.roles
                .stream()
                .map(RoleEntity::getRole)
                .toList();
    }

}