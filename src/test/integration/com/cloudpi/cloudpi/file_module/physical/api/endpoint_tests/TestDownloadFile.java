package com.cloudpi.cloudpi.file_module.physical.api.endpoint_tests;

import com.cloudpi.cloudpi.file_module.physical.api.FileAPITestTemplate;
import com.cloudpi.cloudpi.utils.controller_tests.ControllerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;

@ControllerTest
public class TestDownloadFile extends FileAPITestTemplate {

    @BeforeEach
    void setUp() throws Exception {
        initTemplate();
    }

    @AfterAll
    void clearDir() throws Exception {
        _clearStorageDirectory();
    }

    //todo
//    @Test
//    @WithUser(username = "bob", authorities = Role.user)
//    void should_download_save_file() throws Exception {
//        //given
//        var fileInfo = filesystemAPI.se
//
//        //when
//        var fileResponse = mockMvc.perform(
//                        fileAPI.downloadFileReqBuilder(fileInfo.getPubId()))
//                .andExpect(status().is2xxSuccessful())
//                .andReturn()
//                .getResponse();
//
//        //then
//        assert fileResponse.getContentAsString()
//                .equals(fileAPI.textfileContent);
//
//    }
//
//    @Test
//    @WithUser(username = "Alice", authorities = Role.user)
//    void should_return_403_when_user_has_no_permission() throws Exception {
//        //given
//        var fileInfo = uploadTextTxtAsBob();
//
//        //when
////        fileAPI.
//        //todo
//    }


}
