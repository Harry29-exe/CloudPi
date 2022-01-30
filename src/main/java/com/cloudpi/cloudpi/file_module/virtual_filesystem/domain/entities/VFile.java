package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities;

import com.cloudpi.cloudpi.file_module.permission.domain.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.physical.domain.Drive;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.VFileDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import lombok.*;
import org.springframework.lang.Nullable;

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
    @JoinColumn(name = "parent_id")
    private VFile parent;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private @NotNull FileType type = FileType.UNDEFINED;

    /**
     * Is null only for directories
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drive_files")
    private @Nullable Drive drive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id", nullable = false)
    private @NotNull VFilesystemRoot root;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
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
            @Nullable Drive drive,
            @NotNull FileType type,
            @Min(0) @NotNull Long size
    ) {
        this(name, path, type, drive, size);
        this.parent = parent;
    }

    protected VFile(String name, String path, @NonNull FileType type, @Nullable Drive drive, Long size) {
        if(type != FileType.DIRECTORY && drive == null) {
            throw new IllegalStateException("Drive can only be null if file is directory");
        }
        this.name = name;
        this.path = path;
        this.type = type;
        this.drive = drive;
        this.root = parent.getRoot();
        this.details = new VFileDetails(size, this);
    }

    public static VFile createRootDir(
            @NotBlank String username
    ) {
        return new VFile(username, username, FileType.DIRECTORY, null, 0L);
    }

    public static VFile createDirectory(
            @NotBlank String name,
            @NotBlank String path
    ) {
        return new VFile(name, path, FileType.DIRECTORY, null, 0L);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public VFileDTO mapToDTO() {
        return new VFileDTO(
                pubId,
                name,
                path,
                parent.getPubId(),
                details.getHasThumbnail(),
                type,
                details.getSize(),
                details.getModifiedAt(),
                details.getCreatedAt()
        );
    }

}