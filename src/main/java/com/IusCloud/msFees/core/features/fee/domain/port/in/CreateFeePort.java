package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;

import java.util.UUID;

public interface CreateFeePort {
    FeeResponseDTO execute(FeeRequestDTO request, UUID tenantId);
}
