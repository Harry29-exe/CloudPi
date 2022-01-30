package com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities;

import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "virtual_drives")
public class VFilesystemRoot {

    public VFilesystemRoot(Long assignedCapacity, UserEntity owner) {
        this.assignedCapacity = assignedCapacity;
        this.owner = owner;
        owner.setUserDrive(this);
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long assignedCapacity;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, updatable = false)
    private UserEntity owner;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "root_directory")
    private VFile rootVDirectory;

    @PrePersist
    void checkIdRootDirectoryNotNull() {
        if (rootVDirectory == null) {
            throw new IllegalStateException("Virtual drive must have assigned root directory");
        }
    }

    public void setRootVDirectory(VFile rootVDirectory) {
        this.rootVDirectory = rootVDirectory;
        rootVDirectory.setRoot(this);
    }
}
