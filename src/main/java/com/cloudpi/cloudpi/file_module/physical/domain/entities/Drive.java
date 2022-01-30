package com.cloudpi.cloudpi.file_module.physical.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Drive {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID pubId;



}
