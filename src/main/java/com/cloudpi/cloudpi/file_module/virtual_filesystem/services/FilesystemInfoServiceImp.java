package com.cloudpi.cloudpi.file_module.virtual_filesystem.services;

import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FilesystemRootInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.structure.FileStructureDTO;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.VirtualPath;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories.FilesystemRootInfoRepo;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.AppService;
import org.springframework.beans.factory.annotation.Value;

@AppService
public class FilesystemInfoServiceImp implements FilesystemInfoService {

    private final Long defaultSpaceOnVirtualDrive;
    private final UserRepo userRepository;
    private final FilesystemRootInfoRepo virtualDriveRepo;

    public FilesystemInfoServiceImp(
            @Value("${cloud-pi.storage.default-space-on-virtual-drive}")
                    String spaceOnVD,
            UserRepo userRepo,
            FilesystemRootInfoRepo virtualDriveRepo) {

        this.defaultSpaceOnVirtualDrive =
                Long.parseLong(spaceOnVD.replace("_", ""));
        this.userRepository = userRepo;
        this.virtualDriveRepo = virtualDriveRepo;
    }

    @Override
    public void createRoot(Long userId, Long driveSize) {
        var user = userRepository.findById(userId)
                .orElseThrow(ResourceNotExistException::new);

        var usersDrive = new FilesystemRootInfo(driveSize, user);
        var rootDir = FileInfo.createRootDir(user.getUsername());
        usersDrive.setRootVDirectory(rootDir);

        virtualDriveRepo.save(usersDrive);
    }

    @Override
    public void createRoot(Long userId) {
        this.createRoot(userId, this.defaultSpaceOnVirtualDrive);
    }

    @Override
    public FileStructureDTO get(VirtualPath entryPoint, Integer depth, String username) {
        return null;
    }

}
