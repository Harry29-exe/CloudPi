package com.cloudpi.cloudpi.file_module.sharing.domain;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.sharing.api.requests.ShareFileRequest;
import com.cloudpi.cloudpi.file_module.sharing.dto.UserSharedFilesDTO;
import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Sharing {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id")
    private @NotNull FilePermission permission;

    public Sharing(UserEntity owner, FilePermission permission) {
        this.owner = owner;
        this.permission = permission;
    }
}
