package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.Directory;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VFilesystemRoot;
import com.cloudpi.cloudpi_backend.files.filesystem.repositories.FilesystemRootRepo;
import com.cloudpi.cloudpi_backend.user.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VFilesystemServiceImp implements VFilesystemService {

    private final Long defaultSpaceOnVirtualDrive;
    private final UserRepository userRepository;
    private final FilesystemRootRepo virtualDriveRepo;

    public VFilesystemServiceImp(
            @Value("${cloud-pi.storage.default-space-on-virtual-drive}")
                    String spaceOnVD,
            UserRepository userRepository, FilesystemRootRepo virtualDriveRepo) {

        this.defaultSpaceOnVirtualDrive =
                Long.parseLong(spaceOnVD.replace("_", ""));
        this.userRepository = userRepository;
        this.virtualDriveRepo = virtualDriveRepo;
    }

    @Override
    public void createVirtualFilesystem(Long userId, Long driveSize) {
        var user = userRepository.findById(userId)
                .orElseThrow(NoSuchUserException::notFoundById);
        var usersDrive = new VFilesystemRoot(driveSize, user);
        var rootDir = new Directory(null, usersDrive, user.getUsername());
        usersDrive.setRootDirectory(rootDir);

        virtualDriveRepo.save(usersDrive);
    }

    @Override
    public void createVirtualFilesystem(Long userId) {
        this.createVirtualFilesystem(userId, this.defaultSpaceOnVirtualDrive);
    }
}
