package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.FeeMapper;
import com.IusCloud.msFees.core.features.fee.domain.port.in.GetFeeByIdPort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetFeeByIdUseCase implements GetFeeByIdPort {

    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;

    @Override
    public FeeResponseDTO execute(UUID id, UUID tenantId) {
        return feeRepository.findById(id, tenantId)
                .map(feeMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", id));
    }
}
