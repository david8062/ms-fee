package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;

import java.util.UUID;

public interface AddTimeEntryPort {
    TimeEntryResponseDTO execute(UUID feeId, TimeEntryRequestDTO request, UUID tenantId);
}
