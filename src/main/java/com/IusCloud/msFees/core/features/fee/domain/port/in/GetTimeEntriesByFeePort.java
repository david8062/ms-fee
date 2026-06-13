package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface GetTimeEntriesByFeePort {
    List<TimeEntryResponseDTO> execute(UUID feeId, UUID tenantId);
}
