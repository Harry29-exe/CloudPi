package com.cloudpi.cloudpi.file_module.filesystem.dto;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.dto.TimePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;


@Schema(description = "All values are nullable (but if created or lastModified is supplied it has to contain both from and to dates).")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileQueryDTO {

    @Schema(nullable = true, description = "nullable")
    private String name;

    @Schema(nullable = true, description = "nullable")
    private String path;

    @Schema(nullable = true, description = "nullable")
    private List<FileType> types;

    @Schema(nullable = true, description = "nullable")
    @Valid
    @Nullable
    private TimePeriod created;

    @Schema(nullable = true, description = "nullable")
    @Valid
    @Nullable
    private TimePeriod lastModified;


}
