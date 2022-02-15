package com.cloudpi.cloudpi.file_module.filesystem.domain;

import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.structure.FilesystemObjectDTO;
import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.file_module.filesystem.services.dto.UpdateVFile;
import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.physical.domain.Drive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private @NotNull FileDetails details;

    /**
     * if it's null it means that the parents is root
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private FileInfo parent;

    @Column(name = "parent_id")
    private Long parentId;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "file"
    )
    private List<FileAncestor> ancestors;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "ancestor")
    private List<FileAncestor> children;

    /**
     * Is null only for directories
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_files")
    private @Nullable
    Drive drive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "root_id", nullable = false)
    private @NotNull FilesystemInfo root;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "permissions")
    private List<FilePermission> permissions;

    //------Constructors------
    public FileInfo(
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
        this.path = parent.getPath() + "/" + name;
        this.type = type;
        this.drive = drive;
        this.root = parent.getRoot();
        this.details = new FileDetails(size, this);

        this.parentId = parent.getParentId();
        this.createAncestors(parent);
    }

    private FileInfo(String name, String path, @NonNull FileType type, Long size) {
        if (type != FileType.DIRECTORY && drive == null) {
            throw new IllegalStateException("Drive can only be null if file is directory");
        }
        this.name = name;
        this.path = path;
        this.type = type;
        this.details = new FileDetails(size, this);
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
            @NotNull FileInfo parent
    ) {
        return new FileInfo(name, parent, null, FileType.DIRECTORY, 0L);
    }


    //------Methods------
    public void update(UpdateVFile updateVFile) {
        this.name = updateVFile.getNewName();
    }

    public void move(FileInfo newParent) {
        ancestors.forEach(
                ancestor -> ancestor.setFile(null)
        );

        ancestors = new ArrayList<>();
        ancestors.add(
                new FileAncestor(this, newParent.getId(), 1)
        );

        for (var parentAncestor : newParent.ancestors) {
            ancestors.add(
                    transformToThisFileAncestor(parentAncestor)
            );
        }
    }

    public FileInfoDTO mapToDTO() {
        return new FileInfoDTO(
                pubId,
                name,
                path,
                details.getHasThumbnail(),
                type,
                details.getSize(),
                details.getModifiedAt(),
                details.getCreatedAt(),
                details.getIsFavourite()
        );
    }

    public FilesystemObjectDTO mapToFilesystemObjectDTO(List<FilesystemObjectDTO> children) {
        return new FilesystemObjectDTO(
                pubId,
                name,
                details.getSize(),
                details.getModifiedAt(),
                fileVersion,
                type,
                children
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

    private void createAncestors(FileInfo parent) {
        var ancestors = parent.getAncestors()
                .stream()
                .map(parentAncestor ->
                        new FileAncestor(this, parentAncestor.getAncestorId(), parentAncestor.getTreeLevelDiff() + 1))
                .collect(Collectors.toCollection(ArrayList::new));
        ancestors.add(
                new FileAncestor(this, parent.getId(), 1)
        );

        this.ancestors = ancestors;
    }

    private FileAncestor transformToThisFileAncestor(FileAncestor parentAncestor) {
        return new FileAncestor(this, parentAncestor.getAncestorId(), parentAncestor.getTreeLevelDiff() + 1);
    }

}