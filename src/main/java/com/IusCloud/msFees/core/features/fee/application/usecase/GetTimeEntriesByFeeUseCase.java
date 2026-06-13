package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.TimeEntryMapper;
import com.IusCloud.msFees.core.features.fee.domain.port.in.GetTimeEntriesByFeePort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.TimeEntryRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetTimeEntriesByFeeUseCase implements GetTimeEntriesByFeePort {

    private final FeeRepository feeRepository;
    private final TimeEntryRepository timeEntryRepository;
    private final TimeEntryMapper timeEntryMapper;

    @Override
    public List<TimeEntryResponseDTO> execute(UUID feeId, UUID tenantId) {
        feeRepository.findById(feeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", feeId));
        return timeEntryRepository.findByFeeId(feeId).stream()
                .map(timeEntryMapper::toResponse)
                .toList();
    }
}
