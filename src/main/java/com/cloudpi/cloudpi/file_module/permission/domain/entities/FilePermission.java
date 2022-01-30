package com.cloudpi.cloudpi.file_module.permission.domain.entities;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.VFile;
import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
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

    @ManyToOne
    @JoinColumn
    private UserEntity user;

    @ManyToOne
    @JoinColumn
    private VFile file;

}
