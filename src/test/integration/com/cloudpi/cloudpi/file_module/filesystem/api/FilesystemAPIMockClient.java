package com.cloudpi.cloudpi.file_module.filesystem.api;

import com.cloudpi.cloudpi.file_module.filesystem.api.request.MoveFileRequest;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileInfoDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FileQueryDTO;
import com.cloudpi.cloudpi.file_module.filesystem.dto.FilesystemInfoDTO;
import com.cloudpi.cloudpi.utils.api_tests.AbstractAPIMockClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static com.cloudpi.cloudpi.utils.api_tests.MockMvcUtils.getBody;
import static com.cloudpi.cloudpi.utils.api_tests.MockMvcUtils.getBodyAsList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("controller-test")
@Component
public class FilesystemAPIMockClient extends AbstractAPIMockClient {

    private final String apiAddr = "/filesystem/";

    //--------getFileStructure---------
    public MockHttpServletRequestBuilder getFileStructureRequest(
            @Nullable Integer structureLevels,
            @Nullable String fileStructureRoot
    ) {
        var requestBuilder = post(apiAddr + "file-structure");

        if (structureLevels != null)
            requestBuilder = requestBuilder
                    .param("structureLevels", structureLevels.toString());
        if (fileStructureRoot != null)
            requestBuilder = requestBuilder
                    .param("fileStructureRoot", fileStructureRoot);

        return requestBuilder;
    }

    public List<FileInfoDTO> getFileStructure(
            @Nullable Integer structureLevels,
            @Nullable String fileStructureRoot
    ) throws Exception {

        var response = perform(
                getFileStructureRequest(structureLevels, fileStructureRoot)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, FileInfoDTO.class);
    }

    public ResultActions performGetFileStructure(
            @Nullable Integer structureLevels,
            @Nullable String fileStructureRoot
    ) throws Exception {

        return perform(
                getFileStructureRequest(structureLevels, fileStructureRoot)
        );
    }

    public ResultActions performGetFileStructure(
            @Nullable Integer structureLevels,
            @Nullable String fileStructureRoot,
            String asUsername
    ) throws Exception {

        return perform(
                getFileStructureRequest(structureLevels, fileStructureRoot),
                asUsername
        );
    }



    //--------getFilesSharedByUser---------
    public MockHttpServletRequestBuilder getFilesSharedByUserRequest() {
        return get(apiAddr + "files-shared-by-user");
    }

    public List<FileInfoDTO> getFilesSharedByUser() throws Exception {

        var response = perform(
                getFilesSharedByUserRequest()
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, FileInfoDTO.class);
    }

    public ResultActions performGetFilesSharedByUser(
    ) throws Exception {

        return perform(
                getFilesSharedByUserRequest()
        );
    }

    public ResultActions performGetFilesSharedByUser(String asUsername) throws Exception {

        return perform(
                getFilesSharedByUserRequest(),
                asUsername
        );
    }



    //--------getFilesSharedToUser---------
    public MockHttpServletRequestBuilder getFilesSharedToUserRequest() {
        return get(apiAddr + "files-shared-to-user");
    }

    public List<FileInfoDTO> getFilesSharedToUser() throws Exception {

        var response = perform(
                getFilesSharedToUserRequest()
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, FileInfoDTO.class);
    }

    public ResultActions performGetFilesSharedToUser(
    ) throws Exception {

        return perform(
                getFilesSharedToUserRequest()
        );
    }

    public ResultActions performGetFilesSharedToUser(String asUsername) throws Exception {

        return perform(
                getFilesSharedToUserRequest(),
                asUsername
        );
    }


    //--------createDirectory---------
    public MockHttpServletRequestBuilder createDirectoryRequest(String directoryPath) {
        return put(apiAddr + "directory")
                .param("directoryPath", directoryPath);
    }

    public FileInfoDTO createDirectory(String directoryPath) throws Exception {

        var response = perform(
                createDirectoryRequest(directoryPath)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performCreateDirectory(
            String directoryPath
    ) throws Exception {

        return perform(
                createDirectoryRequest(directoryPath)
        );
    }

    public ResultActions performCreateDirectory(String directoryPath, String asUsername) throws Exception {

        return perform(
                createDirectoryRequest(directoryPath),
                asUsername
        );
    }


    //--------getFileInfo---------
    public MockHttpServletRequestBuilder getFileInfoByPathRequest(
            String filePath,
            @Nullable Boolean getWithPermissions
    ) {
        var requestBuilder = get(apiAddr + "file/" + filePath + "/by-path");

        if (getWithPermissions != null)
            requestBuilder = requestBuilder
                    .param("getWithPermissions", getWithPermissions.toString());

        return requestBuilder;
    }

    public FileInfoDTO getFileInfoByPath(
            String filePath,
            @Nullable Boolean getWithPermissions
    ) throws Exception {

        var response = perform(
                getFileInfoByPathRequest(filePath, getWithPermissions)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performGetFileInfoByPath(
            String filePath,
            @Nullable Boolean getWithPermissions
    ) throws Exception {

        return perform(
                getFileInfoByPathRequest(filePath, getWithPermissions)
        );
    }

    public ResultActions performGetFileInfoByPath(
            String filePath,
            @Nullable Boolean getWithPermissions,
            String asUsername
    ) throws Exception {

        return perform(
                getFileInfoByPathRequest(filePath, getWithPermissions),
                asUsername
        );
    }


    //--------getFileInfo---------
    public MockHttpServletRequestBuilder getFileInfoRequest(
            UUID fileId,
            @Nullable Boolean getWithPermissions
    ) {
        var requestBuilder = get(apiAddr + "file/" + fileId.toString());

        if (getWithPermissions != null)
            requestBuilder = requestBuilder
                    .param("getWithPermissions", getWithPermissions.toString());

        return requestBuilder;
    }

    public FileInfoDTO getFileInfo(
            UUID fileId,
            @Nullable Boolean getWithPermissions
    ) throws Exception {

        var response = perform(
                getFileInfoRequest(fileId, getWithPermissions)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performGetFileInfo(
            UUID fileId,
            @Nullable Boolean getWithPermissions
    ) throws Exception {

        return perform(
                getFileInfoRequest(fileId, getWithPermissions)
        );
    }

    public ResultActions performGetFileInfo(
            UUID fileId,
            @Nullable Boolean getWithPermissions,
            String asUsername
    ) throws Exception {

        return perform(
                getFileInfoRequest(fileId, getWithPermissions),
                asUsername
        );
    }


    //--------moveFile---------
    public MockHttpServletRequestBuilder moveFileRequest(
            MoveFileRequest request
    ) throws Exception {

        return patch(apiAddr + "move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request));
    }

    public FileInfoDTO moveFile(
            MoveFileRequest request
    ) throws Exception {

        var response = perform(
                moveFileRequest(request)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBody(response, FileInfoDTO.class);
    }

    public ResultActions performMoveFile(
            MoveFileRequest request
    ) throws Exception {

        return perform(
                moveFileRequest(request)
        );
    }

    public ResultActions performMoveFile(
            MoveFileRequest request,
            String asUsername
    ) throws Exception {

        return perform(
                moveFileRequest(request),
                asUsername
        );
    }


    //--------deleteDirectory---------
    public MockHttpServletRequestBuilder deleteDirectoryRequest(String directoryPubId) throws Exception {

        return delete(apiAddr + "directory/" + directoryPubId);

    }

    public void deleteDirectory(String directoryPubId) throws Exception {

        perform(
                deleteDirectoryRequest(directoryPubId)
        )
                .andExpect(status().is2xxSuccessful());
    }

    public ResultActions performDeleteDirectory(String directoryPubId) throws Exception {

        return perform(
                deleteDirectoryRequest(directoryPubId)
        );
    }

    public ResultActions performDeleteDirectory(String directoryPubId, String asUsername) throws Exception {

        return perform(
                deleteDirectoryRequest(directoryPubId),
                asUsername
        );
    }


    //--------getUsersFilesystemInfo---------
    public MockHttpServletRequestBuilder getUsersFilesystemInfoRequest(
            String username
    ) throws Exception {

        return get(apiAddr + username);

    }

    public FilesystemInfoDTO getUsersFilesystemInfo(
            String username
    ) throws Exception {

        var response = perform(
                getUsersFilesystemInfoRequest(username)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBody(response, FilesystemInfoDTO.class);
    }

    public ResultActions performGetUsersFilesystemInfo(
            String username
    ) throws Exception {

        return perform(
                getUsersFilesystemInfoRequest(username)
        );
    }

    public ResultActions performGetUsersFilesystemInfo(
            String username,
            String asUsername
    ) throws Exception {

        return perform(
                getUsersFilesystemInfoRequest(username),
                asUsername
        );
    }


    //--------changeUserFilesystemMaxSize---------
    public MockHttpServletRequestBuilder changeUserFilesystemMaxSizeRequest(
            String username, Long newAssignedSpace
    ) throws Exception {
        var requestBuilder = post(apiAddr + username);

        return requestBuilder;
    }

    public void changeUserFilesystemMaxSize(
            String username, Long newAssignedSpace
    ) throws Exception {

        var response = perform(
                changeUserFilesystemMaxSizeRequest(username, newAssignedSpace)
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    public ResultActions performChangeUserFilesystemMaxSize(
            String username, Long newAssignedSpace
    ) throws Exception {

        return perform(
                changeUserFilesystemMaxSizeRequest(username, newAssignedSpace)
        );
    }

    public ResultActions performChangeUserFilesystemMaxSize(
            String username, Long newAssignedSpace,
            String asUsername
    ) throws Exception {

        return perform(
                changeUserFilesystemMaxSizeRequest(username, newAssignedSpace),
                asUsername
        );
    }


    //--------searchInUserFiles---------
    public MockHttpServletRequestBuilder searchInUserFilesRequest(FileQueryDTO requestBody) throws Exception {
        return post(apiAddr + "search")
                .content(mapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON);
    }

    public List<FileInfoDTO> searchInUserFiles(FileQueryDTO requestBody) throws Exception {
        var response = mockMvc.perform(
                        searchInUserFilesRequest(requestBody)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return getBodyAsList(response, FileInfoDTO.class);
    }

    public ResultActions performSearchInUserFiles(FileQueryDTO requestBody) throws Exception {
        return perform(
                searchInUserFilesRequest(requestBody)
        );
    }

    public ResultActions performSearchInUserFiles(FileQueryDTO requestBody, String asUsername) throws Exception {
        return perform(
                searchInUserFilesRequest(requestBody),
                asUsername
        );
    }

}
