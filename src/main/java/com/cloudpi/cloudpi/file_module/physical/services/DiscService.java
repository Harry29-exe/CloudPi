package com.cloudpi.cloudpi.file_module.physical.services;

import com.cloudpi.cloudpi.file_module.physical.dto.DiscDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DiscService {

    List<DiscDTO> getCurrentlyAvailableDisc();

}
