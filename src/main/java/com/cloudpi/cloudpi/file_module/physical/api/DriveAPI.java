package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.file_module.physical.dto.DiscDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("drive")
public interface DriveAPI {

    @GetMapping("discs")
    List<DiscDTO> getAllDiscs();



}
