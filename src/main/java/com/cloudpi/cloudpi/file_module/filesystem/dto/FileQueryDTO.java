package com.cloudpi.cloudpi.file_module.filesystem.dto;

import com.cloudpi.cloudpi.file_module.filesystem.pojo.FileType;
import com.cloudpi.cloudpi.utils.dto.TimePeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.annotation.Nullable;
import javax.validation.Valid;


@Schema(description = "All values are nullable (but if created or lastModified is supplied it has to contain both from and to dates).")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileQueryDTO {

    private String name;
    private FileType type;
    @Valid
    @Nullable
    private TimePeriod created;
    @Valid
    @Nullable
    private TimePeriod lastModified;


}
