package com.cloudpi.cloudpi.file_module.virtual_filesystem.dto;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.pojo.FileType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;


@Schema(description = "All values are nullable. Search works based on scores for every category. " +
        "If particular file has more score than thresholds its get returned")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileSearchQueryDTO {

    @Schema(description = "File name. Score: 10")
    private String name;
    @Schema(description = "Score: 5")
    private FileType type;
    @Schema(description = "Score: 2")
    private Date from;
    @Schema(description = "Score: 2")
    private Date to;


}
