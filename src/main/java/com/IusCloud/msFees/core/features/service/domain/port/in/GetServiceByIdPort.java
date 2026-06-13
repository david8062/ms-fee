package com.IusCloud.msFees.core.features.service.domain.port.in;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;

import java.util.UUID;

public interface GetServiceByIdPort {
    ServiceResponseDTO execute(UUID id, UUID tenantId);
}
