package com.cloudpi.cloudpi.file_module.physical.domain;

import com.cloudpi.cloudpi.file_module.filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
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
    @GeneratedValue
    private Long id;

    @Column
    private UUID pubId = UUID.randomUUID();

    @Column(nullable = false, unique = true, updatable = false)
    private String path;

    @Column(nullable = false)
    private Long assignedSpace;

    @Column(nullable = false)
    private Long freeSpace;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drive")
    private Set<FileInfo> files;

    public double calcFillPerc() {
        return (double) (assignedSpace - freeSpace) / assignedSpace;
    }

    public Drive(String path, Long assignedSpace) {
        this.path = path;
        this.assignedSpace = assignedSpace;
        this.freeSpace = assignedSpace;
    }

    public DriveDTO mapToDTO() {
        return new DriveDTO(
                pubId,
                path,
                assignedSpace,
                freeSpace
        );
    }

}
