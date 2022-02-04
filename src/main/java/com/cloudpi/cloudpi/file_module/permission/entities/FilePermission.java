package com.cloudpi.cloudpi.file_module.permission.entities;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class FilePermission {

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
