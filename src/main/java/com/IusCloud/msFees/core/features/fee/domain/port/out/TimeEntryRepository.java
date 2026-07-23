package com.IusCloud.msFees.core.features.fee.domain.port.out;

import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeEntryRepository {
    TimeEntryEntity save(TimeEntryEntity entry);
    Optional<TimeEntryEntity> findByIdAndFeeId(UUID id, UUID feeId);
    List<TimeEntryEntity> findByFeeId(UUID feeId);
    void delete(TimeEntryEntity entry);
}
