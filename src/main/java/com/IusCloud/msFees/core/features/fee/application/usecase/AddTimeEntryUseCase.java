package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.TimeEntryMapper;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.AddTimeEntryPort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.TimeEntryRepository;
import com.IusCloud.msFees.shared.enums.FeeTypeEnum;
import com.IusCloud.msFees.shared.exceptions.BusinessException;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddTimeEntryUseCase implements AddTimeEntryPort {

    private final FeeRepository feeRepository;
    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;

    @Transactional
    @Override
    public TimeEntryResponseDTO execute(UUID feeId, TimeEntryRequestDTO request, UUID tenantId) {
        FeeEntity fee = feeRepository.findById(feeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", feeId));

        if (fee.getFeeType() != FeeTypeEnum.HOURLY) {
            throw new BusinessException("El registro de tiempo solo aplica para honorarios de tipo HOURLY");
        }

        TimeEntryEntity entry = timeEntryMapper.toEntity(request);
        entry.setFee(fee);
        return timeEntryMapper.toResponse(timeEntryRepository.save(entry));
    }
}
