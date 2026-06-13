package com.IusCloud.msFees.core.features.service.domain.port.in;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetAllServicesPort {
    Page<ServiceResponseDTO> execute(UUID tenantId, Boolean isPublic, Pageable pageable);
}
