package com.cloudpi.cloudpi.file_module;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.controller_tests.AbstractAPITestTemplate;
import com.google.common.collect.ImmutableList;
import lombok.Value;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

public abstract class FileModuleAPITestTemplate extends AbstractAPITestTemplate {

    protected List<UploadFileParams> filesToUpload = ImmutableList.of(

    );

    protected List<CreateDirParams> directoriesToUpload = ImmutableList.of(
//        new
    );

    protected void createBasicFileStructure() {

    }

    @Value
    protected static class UploadFileParams {
        MockMultipartFile file;
        FileType fileType;
        String filepath;
    }

    @Value
    protected static class CreateDirParams {
        String dirPath;
    }

}
