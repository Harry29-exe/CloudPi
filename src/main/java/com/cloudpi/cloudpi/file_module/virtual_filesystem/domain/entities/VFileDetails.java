package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VFileDetails {

    @Id
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private @NotNull Long size;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private @NotNull Date modifiedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private @NotNull Date createdAt;

    @Column
    private Boolean hasThumbnail = false;

    @MapsId
    @OneToOne
    @JoinColumn(name = "file_id")
    private VFile file;

    public VFileDetails(Long size, VFile file) {
        this.size = size;
        this.file = file;
    }
}
