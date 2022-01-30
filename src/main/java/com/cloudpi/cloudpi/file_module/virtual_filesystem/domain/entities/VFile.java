package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities;

import com.cloudpi.cloudpi.file_module.permission.domain.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.physical.domain.entities.Drive;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VFile{

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID pubId = UUID.randomUUID();

    @Column(nullable = false)
    private @NotNull String name;

    @Column(unique = true, nullable = false)
    private @NotBlank String path;

    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id", nullable = true)
    private VFile parent;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private @NotNull FileType type = FileType.UNDEFINED;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drive_files")
    private @NotNull Drive drive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id", nullable = false)
    private @NotNull VFilesystemRoot root;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private @NotNull VFileDetails details;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    private List<FilePermission> permissions;

    public VFile(
            @NotBlank String path,
            @NotBlank String name,
            @NonNull VFile parent,
            @NonNull VFilesystemRoot root,
            @NonNull Drive drive,
            @NotNull FileType type,
            @Min(0) @NotNull Long size
    ) {
        this(name, path, type, drive, root, size);
        this.parent = parent;
    }

    protected VFile(String name, String path, @NonNull FileType type, Drive drive, VFilesystemRoot root, Long size) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.drive = drive;
        this.root = root;
        this.details = new VFileDetails(size, this);
    }

    public VFile createRootDir(
            @NotBlank String name,
            @NotBlank String path,
            @NonNull FileType fileType,
            @NotNull Drive drive,
            @NotNull VFilesystemRoot root,
            @Min(0) Long size) {
        return new VFile(name, path, fileType, drive, root, size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}