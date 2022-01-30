package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.VFile;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.entities.VFilesystemRoot;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.repositories.VFilesystemRootRepo;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VFilesystemServiceImp implements VFilesystemService {

    private final Long defaultSpaceOnVirtualDrive;
    private final UserRepo userRepository;
    private final VFilesystemRootRepo virtualDriveRepo;

    public VFilesystemServiceImp(
            @Value("${cloud-pi.storage.default-space-on-virtual-drive}")
            String spaceOnVD,
            UserRepo userRepo,
            VFilesystemRootRepo virtualDriveRepo) {

        this.defaultSpaceOnVirtualDrive =
                Long.parseLong(spaceOnVD.replace("_", ""));
        this.userRepository = userRepo;
        this.virtualDriveRepo = virtualDriveRepo;
    }

    @Override
    public void createVirtualFilesystem(Long userId, Long driveSize) {
        var user = userRepository.findById(userId)
                .orElseThrow(ResourceNotExistException::new);

        var usersDrive = new VFilesystemRoot(driveSize, user);
        var rootDir = VFile.createRootDir(user.getUsername());
        usersDrive.setRootVDirectory(rootDir);

        virtualDriveRepo.save(usersDrive);
    }

    @Override
    public void createVirtualFilesystem(Long userId) {
        this.createVirtualFilesystem(userId, this.defaultSpaceOnVirtualDrive);
    }
}
