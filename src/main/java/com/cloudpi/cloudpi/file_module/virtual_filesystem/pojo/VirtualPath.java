package com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class VirtualPath {
    @NotBlank
    private final String username;
    @NotBlank
    private final String parentPath;
    @NotBlank
    private final String path;
    @NotBlank
    private final String name;
    private ImmutableList<String> directories = null;

    public VirtualPath(String path) {
        int incorrectIndex = path.indexOf("//");
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (incorrectIndex >= 0) {
            //TODO change exception
            throw new IllegalArgumentException("Incorrect path");
        }
        this.path = path;
        int fileNameIndex = path.indexOf('/');
        if (fileNameIndex < 0) {
            username = path;
            parentPath = null;
            name = path;
        } else {
            username = path.substring(0, fileNameIndex);
            var lastSlashIndex = path.lastIndexOf('/');
            parentPath = path.substring(0, lastSlashIndex);
            name = path.substring(lastSlashIndex + 1);
        }
    }

    public ImmutableList<String> getDirectoriesInPath() {
        return directories == null ?
                this.pathToDirectories() :
                directories;
    }

    private ImmutableList<String> pathToDirectories() {
        var pathParts = List.of(parentPath.split("/"));
        directories = (ImmutableList<String>) pathParts.subList(0, pathParts.size() - 1);
        return directories;
    }

    public String getUsername() {
        return username;
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
