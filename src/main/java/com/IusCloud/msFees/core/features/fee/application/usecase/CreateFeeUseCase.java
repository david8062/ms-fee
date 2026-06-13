package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.FeeMapper;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.CreateFeePort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateFeeUseCase implements CreateFeePort {

    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;

    @Transactional
    @Override
    public FeeResponseDTO execute(FeeRequestDTO request, UUID tenantId) {
        FeeEntity entity = feeMapper.toEntity(request, tenantId);
        return feeMapper.toResponse(feeRepository.save(entity));
    }
}
