package com.cloudpi.cloudpi.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TimePeriod {

    @NotNull
    private Date from;
    @NotNull
    private Date to;

}
