package com.cloudpi.cloudpi.file_module.filesystem.domain;

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
public class FileDetails {

    @Id
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private @NotNull Long size;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private @NotNull Date modifiedAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private @NotNull Date createdAt = new Date();

    @Column
    private Boolean hasThumbnail = false;

    @Column
    private Boolean isFavourite = false;

    @MapsId
    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo file;

    public FileDetails(Long size, FileInfo file) {
        this.size = size;
        this.file = file;
    }
}
