package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.DeleteTimeEntryPort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.TimeEntryRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteTimeEntryUseCase implements DeleteTimeEntryPort {

    private final FeeRepository feeRepository;
    private final TimeEntryRepository timeEntryRepository;

    @Transactional
    @Override
    public void execute(UUID entryId, UUID feeId, UUID tenantId) {
        feeRepository.findById(feeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", feeId));

        TimeEntryEntity entry = timeEntryRepository.findByIdAndFeeId(entryId, feeId)
                .orElseThrow(() -> new ResourceNotFoundException("TimeEntry", entryId));

        timeEntryRepository.delete(entry);
    }
}
