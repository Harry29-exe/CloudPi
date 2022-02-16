package com.cloudpi.cloudpi.file_module.filesystem.api;

import com.cloudpi.cloudpi.file_module.FileModuleAPITestTemplate;

public class FilesystemTestTemplate extends FileModuleAPITestTemplate {

    protected void initTemplate() throws Exception {
        initUsersToDB();
        initDrive();
        initBasicFileStructure();
    }

}