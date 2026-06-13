package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.FeeMapper;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.UpdateFeePort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.shared.exceptions.BusinessException;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateFeeUseCase implements UpdateFeePort {

    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;

    @Transactional
    @Override
    public FeeResponseDTO execute(UUID id, FeeRequestDTO request, UUID tenantId) {
        FeeEntity entity = feeRepository.findById(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", id));

        if (entity.getStatus() == FeeStatusEnum.PAID || entity.getStatus() == FeeStatusEnum.CANCELLED) {
            throw new BusinessException("No se puede modificar un honorario en estado " + entity.getStatus());
        }

        feeMapper.updateEntityFromDto(request, entity);
        return feeMapper.toResponse(feeRepository.save(entity));
    }
}
