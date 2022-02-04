package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain;

import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.physical.domain.Drive;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.services.dto.UpdateVFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
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
public class FileInfo {

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

    @Column
    @Enumerated(EnumType.ORDINAL)
    private @NotNull FileType type = FileType.UNDEFINED;

    @Column
    private Integer fileInfoVersion = 0;

    @Column
    private Integer fileVersion = 0;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "file"
    )
    @PrimaryKeyJoinColumn
    private @NotNull FileInfoDetails details;

    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "parent_id")
    private FileInfo parent;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "parent")
    private List<FileInfo> children;

    /**
     * Is null only for directories
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drive_files")
    private @Nullable Drive drive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id", nullable = false)
    private @NotNull FilesystemRootInfo root;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    private List<FilePermission> permissions;

    public FileInfo(
            @NotBlank String path,
            @NotBlank String name,
            @NonNull FileInfo parent,
            @Nullable Drive drive,
            @NotNull FileType type,
            @Min(0) @NotNull Long size
    ) {
        if (type != FileType.DIRECTORY && drive == null) {
            throw new IllegalStateException("Drive can only be null if file is directory");
        }
        this.name = name;
        this.path = path;
        this.type = type;
        this.drive = drive;
        this.parent = parent;
        this.root = parent.getRoot();
        this.details = new FileInfoDetails(size, this);
    }

    private FileInfo(String name, String path, @NonNull FileType type, Long size) {
        if (type != FileType.DIRECTORY && drive == null) {
            throw new IllegalStateException("Drive can only be null if file is directory");
        }
        this.name = name;
        this.path = path;
        this.type = type;
        this.details = new FileInfoDetails(size, this);
    }

    public static FileInfo createRootDir(@NotBlank String username) {
        return new FileInfo(
                username,
                username,
                FileType.DIRECTORY,
                0L);
    }

    public static FileInfo createDirectory(
            @NotBlank String name,
            @NotBlank String path,
            @NotNull FileInfo parent
    ) {
        return new FileInfo(name, path, parent, null, FileType.DIRECTORY, 0L);
    }

    public void update(UpdateVFile updateVFile) {
        this.name = updateVFile.getNewName();
    }

    public FileInfoDTO mapToDTO() {
        return new FileInfoDTO(
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

    @PreUpdate
    public void updateVersion() {
        this.fileInfoVersion++;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}