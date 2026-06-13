package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.DeleteFeePort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteFeeUseCase implements DeleteFeePort {

    private final FeeRepository feeRepository;

    @Transactional
    @Override
    public void execute(UUID id, UUID tenantId) {
        FeeEntity entity = feeRepository.findById(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", id));
        feeRepository.delete(entity);
    }
}
