package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetAllFeesPort {
    Page<FeeResponseDTO> execute(UUID tenantId, UUID clientId, UUID caseId, UUID assignedUserId, Pageable pageable);
}
