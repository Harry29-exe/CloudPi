package com.cloudpi.cloudpi.file_module.physical.api.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record PostDriveRequest(
        @NotBlank String path,
        @Min(1) Long size
) {
}
