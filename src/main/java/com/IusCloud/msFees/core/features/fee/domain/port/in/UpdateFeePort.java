package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;

import java.util.UUID;

public interface UpdateFeePort {
    FeeResponseDTO execute(UUID id, FeeRequestDTO request, UUID tenantId);
}
