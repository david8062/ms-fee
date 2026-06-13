package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;

import java.util.UUID;

public interface GetFeeByIdPort {
    FeeResponseDTO execute(UUID id, UUID tenantId);
}
