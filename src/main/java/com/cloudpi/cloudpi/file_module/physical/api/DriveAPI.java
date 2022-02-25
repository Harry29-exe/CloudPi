package com.cloudpi.cloudpi.file_module.physical.api;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.config.springdoc.NotImplemented;
import com.cloudpi.cloudpi.file_module.physical.api.requests.PostDriveRequest;
import com.cloudpi.cloudpi.file_module.physical.dto.DiscDTO;
import com.cloudpi.cloudpi.file_module.physical.dto.DriveDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@PreAuthorize("hasRole('" + Role.admin + "')")
@RequestMapping("drive")
public interface DriveAPI {

    @GetMapping("discs")
    @Operation(summary = NotImplemented.PRIORITY_LOW + "Returns all detected discs")
    List<DiscDTO> getAllDiscs();

    @PostMapping("new")
    @Operation(summary = "Creates new cloudpi drive in selected directory")
    DriveDTO createNewDrive(@RequestBody PostDriveRequest request);

    @GetMapping
    @Operation(summary = NotImplemented.PRIORITY_LOW + "Returns all drives")
    List<DriveDTO> getAllDrives();


}
