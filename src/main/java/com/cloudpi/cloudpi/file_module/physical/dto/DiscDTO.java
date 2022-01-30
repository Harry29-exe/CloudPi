package com.cloudpi.cloudpi.file_module.physical.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiscDTO {

    private String discName;
    private Long discSize;
    private Long discSpaceAvailable;

}
