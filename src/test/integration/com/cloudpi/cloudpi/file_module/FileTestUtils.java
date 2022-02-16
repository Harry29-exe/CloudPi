package com.cloudpi.cloudpi.file_module;

import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

@Profile("test")
@Component
public class FileTestUtils {

    public MockMultipartFile readFileFromResources(String pathInResources) {
        try {
            return new MockMultipartFile("file", loadResourceFile(pathInResources));
        } catch (Exception ex) {
            throw new IllegalStateException("Exception: " + ex.getMessage() + " has been thrown.", ex);
        }
    }

    public FileInputStream loadResourceFile(String pathInResources) {
        try {
            URL resources = getClass().getClassLoader().getResource(pathInResources);
            if (resources == null)
                throw new IllegalStateException("No such file in resources");

            var file = new File(resources.toURI());

            return new FileInputStream(file);
        } catch (Exception ex) {
            throw new IllegalStateException("Exception: " + ex.getMessage() + " has been thrown.", ex);
        }
    }

}
