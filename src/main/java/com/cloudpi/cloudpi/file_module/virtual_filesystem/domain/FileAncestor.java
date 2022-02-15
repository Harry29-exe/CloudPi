package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class FileAncestor {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer treeLevelDiff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ancestor", insertable = false, updatable = false)
    private FileInfo ancestor;

    @Column(name = "ancestor")
    private Long ancestorId;

    @ManyToOne
    @JoinColumn
    private FileInfo file;

    public FileAncestor(FileInfo ancestor, FileInfo file, Integer treeLevelDiff) {
        this.treeLevelDiff = treeLevelDiff;
        this.ancestorId = ancestor.getId();
        this.file = file;
    }

    public FileAncestor(FileInfo file, Long ancestorId, Integer treeLevelDiff) {
        this.file = file;
        this.ancestorId = ancestorId;
        this.treeLevelDiff = treeLevelDiff;
    }
}
