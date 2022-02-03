package com.cloudpi.cloudpi.file_module.physical.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
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

}
