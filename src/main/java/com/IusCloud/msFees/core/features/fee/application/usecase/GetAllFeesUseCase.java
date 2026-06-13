package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.FeeMapper;
import com.IusCloud.msFees.core.features.fee.domain.port.in.GetAllFeesPort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllFeesUseCase implements GetAllFeesPort {

    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;

    @Override
    public Page<FeeResponseDTO> execute(UUID tenantId, UUID clientId, UUID caseId, UUID assignedUserId, Pageable pageable) {
        return feeRepository.findAll(tenantId, clientId, caseId, assignedUserId, pageable)
                .map(feeMapper::toResponse);
    }
}
