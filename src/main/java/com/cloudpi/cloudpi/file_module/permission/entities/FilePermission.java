package com.cloudpi.cloudpi.file_module.permission.entities;

import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.user.domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FilePermission {

    public FilePermission(PermissionType type, UserEntity user, FileInfo file) {
        this.type = type;
        this.user = user;
        this.file = file;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false)
    private PermissionType type;

    @ManyToOne
    @JoinColumn
    private UserEntity user;

    @ManyToOne
    @JoinColumn
    private FileInfo file;
}
