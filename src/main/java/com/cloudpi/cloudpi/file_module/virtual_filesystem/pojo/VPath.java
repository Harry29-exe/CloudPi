package com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;


public class VPath {
    @Getter
    private final String username;
    @Getter
    private final String parentPath;
    @Getter
    private final String path;
    @Getter
    private final String name;
    private ImmutableList<String> directories = null;

    public VPath(String path) {
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
}
