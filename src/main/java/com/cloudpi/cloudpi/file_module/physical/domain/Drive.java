package com.cloudpi.cloudpi.file_module.physical.domain;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Drive {

    @Id
    @Column
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, unique = true, updatable = false)
    private String path;

    @Column(nullable = false)
    private Long assignedSpace;

    @Column(nullable = false)
    private Long freeSpace;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drive")
    private Set<FileInfo> files;

}
