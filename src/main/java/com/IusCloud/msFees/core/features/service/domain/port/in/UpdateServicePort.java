package com.IusCloud.msFees.core.features.service.domain.port.in;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceRequestDTO;
import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;

import java.util.UUID;

public interface UpdateServicePort {
    ServiceResponseDTO execute(UUID id, ServiceRequestDTO request, UUID tenantId);
}
