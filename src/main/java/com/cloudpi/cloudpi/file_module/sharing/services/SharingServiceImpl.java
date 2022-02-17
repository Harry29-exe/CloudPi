package com.cloudpi.cloudpi.file_module.sharing.services;

import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.repositories.FileInfoRepo;
import com.cloudpi.cloudpi.file_module.permission.entities.FilePermission;
import com.cloudpi.cloudpi.file_module.sharing.api.requests.ShareFileRequest;
import com.cloudpi.cloudpi.file_module.sharing.domain.Sharing;
import com.cloudpi.cloudpi.file_module.sharing.domain.SharingRepo;
import com.cloudpi.cloudpi.file_module.sharing.dto.UserSharedFilesDTO;
import com.cloudpi.cloudpi.user.repositiories.UserRepo;
import com.cloudpi.cloudpi.utils.AppService;

import java.util.*;

@AppService
public class SharingServiceImpl implements SharingService {
    private final SharingRepo sharingRepo;
    private final FileInfoRepo fileInfoRepo;
    private final UserRepo userRepo;

    public SharingServiceImpl(SharingRepo sharingRepo, FileInfoRepo fileInfoRepo, UserRepo userRepo) {
        this.sharingRepo = sharingRepo;
        this.fileInfoRepo = fileInfoRepo;
        this.userRepo = userRepo;
    }

    /*
     * zlozonosc (ignorujac gettery)
     * forEach O(n)
     * containsKey O(1)
     * get/put O(1)
     * list add O(1) lub O(n)
     * replace O(1)
     * O(n2) worst case
     */
    @Override
    public List<UserSharedFilesDTO> getSharedFiles(String username) {
        List<UserSharedFilesDTO> sharedFiles = new ArrayList<>();
        var usersFiles = getGroupedSharedFiles(username);

        usersFiles.forEach((user, list) -> {
            sharedFiles.add(new UserSharedFilesDTO(user, list));
        });

        return sharedFiles;
    }

    @Override
    public void shareFiles(ShareFileRequest shareFileRequest, String owner) {
        List<Sharing> sharingList = new LinkedList<>();
        var fileToShare = fileInfoRepo.findByPubId(shareFileRequest.getFilePubId())
                .orElseThrow(ResourceNotExistException::new);
        var fileOwner = userRepo.findByUsername(owner)
                .orElseThrow(ResourceNotExistException::new);

        shareFileRequest.getUserPermissions().forEach(request -> {
            FilePermission newPermission = new FilePermission(
                    request.getType(),
                    userRepo.findByUsername(request.getUsername())
                            .orElseThrow(ResourceNotExistException::new),
                    fileToShare
            );
            Sharing sharing = new Sharing(fileOwner, newPermission);
            sharingList.add(sharing);
            fileToShare.getPermissions().add(newPermission);
        });

        sharingRepo.saveAll(sharingList);
        fileInfoRepo.save(fileToShare);
    }

    private Map<String, List<FileInfoDTO>> getGroupedSharedFiles(String username) {
        Map<String, List<FileInfoDTO>> usersFiles = new HashMap<>();
        sharingRepo.getAllByPermission_User_Username(username)
                .forEach(sharing -> {
                    String user = sharing.getOwner().getUsername();

                    if(!usersFiles.containsKey(user)) {
                        List<FileInfoDTO> filesList = new LinkedList<>();
                        filesList.add(sharing.getPermission().getFile().mapToDTO());
                        usersFiles.put(user, filesList);
                    } else {
                        var file = sharing.getPermission().getFile().mapToDTO();
                        var list = usersFiles.get(user);
                        list.add(file);
                        usersFiles.replace(user, list);
                    }
                });
        return usersFiles;
    }
}
