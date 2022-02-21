package com.cloudpi.cloudpi.file_module.filesystem.api.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record GetFileByPathRequest(
        @NotBlank List<String> filePaths
) {
}
