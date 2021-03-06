package com.cloudpi.cloudpi.file_module.filesystem.domain;

import com.cloudpi.cloudpi.user.domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "filesystems")
public class FilesystemInfo {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long assignedCapacity;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, updatable = false)
    private UserEntity owner;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "root_directory")
    private FileInfo rootVDirectory;

    @OneToMany(
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "root")
    private List<FileInfo> files;

    public FilesystemInfo(Long assignedCapacity, UserEntity owner) {
        this.assignedCapacity = assignedCapacity;
        this.owner = owner;
        owner.setUserDrive(this);
    }

//    @PrePersist
//    void checkIdRootDirectoryNotNull() {
//        if (rootVDirectory == null) {
//            throw new IllegalStateException("Virtual drive must have assigned root directory");
//        }
//    }

    public void setRootVDirectory(FileInfo rootVDirectory) {
        this.rootVDirectory = rootVDirectory;
        rootVDirectory.setRoot(this);
    }
}
