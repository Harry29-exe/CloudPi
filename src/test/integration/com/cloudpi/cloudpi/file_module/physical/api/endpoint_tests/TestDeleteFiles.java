package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.api_tests.APITest;
import com.cloudpi.cloudpi.utils.mock_mvc_users.WithUser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Nested
@APITest
public class TestDeleteFiles extends FileAPITestTemplate {

    @BeforeEach
    void init() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearDir() throws Exception {
        _clearStorageDirectory();
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_delete_single_file() throws Exception {
        //given
        var uploadedFile = _uploadTextTxtFileToBobAsBob();
        var fileInfo = uploadedFile.getFirst();
        var fileId = uploadedFile.getFirst().getPubId();

        //when
        fileAPI.performDeleteFile(fileId)
                .andExpect(status().is(200));

        //then
        filesystemAPI
                .performGetFileInfoByPath(fileInfo.getPath(), null)
                .andExpect(status().is(404));
    }

    @Test
    @WithUser(username = "bob", authorities = Role.user)
    void should_delete_multiple_files() throws Exception {
        //given
        var filesPathToDelete = List.of(
                "bob/dir1/text.txt",
                "bob/hello world.txt"
        );
        var fileToDeletePubIds = new ArrayList<UUID>();
        for (var path : filesPathToDelete) {
            fileToDeletePubIds.add(
                    filesystemAPI
                            .getFileInfoByPath(path, null)
                            .getPubId()
            );
        }

        //when
        fileAPI.performDeleteFiles(fileToDeletePubIds)
                .andExpect(status().is(200));

        //then
        for (var fileId : fileToDeletePubIds) {
            assert _fileDontExist(fileId);
            filesystemAPI.getFileInfo(fileId, null);
        }
    }

    @Test
    @WithUser(username = "Alice", authorities = Role.user)
    void should_not_delete_file_when_user_has_no_permissions() throws Exception {
        //given

        //when

        //then
    }

}
