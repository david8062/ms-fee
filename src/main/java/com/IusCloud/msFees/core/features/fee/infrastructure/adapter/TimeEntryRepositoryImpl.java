package com.IusCloud.msFees.core.features.fee.infrastructure.adapter;

import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.out.TimeEntryRepository;
import com.IusCloud.msFees.core.features.fee.infrastructure.persistence.TimeEntryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TimeEntryRepositoryImpl implements TimeEntryRepository {

    private final TimeEntryJpaRepository jpaRepository;

    @Override
    public TimeEntryEntity save(TimeEntryEntity entry) {
        return jpaRepository.save(entry);
    }

    @Override
    public Optional<TimeEntryEntity> findByIdAndFeeId(UUID id, UUID feeId) {
        return jpaRepository.findByIdAndFeeIdAndDeletedAtIsNull(id, feeId);
    }

    @Override
    public List<TimeEntryEntity> findByFeeId(UUID feeId) {
        return jpaRepository.findByFeeIdAndDeletedAtIsNull(feeId);
    }

    @Override
    public void delete(TimeEntryEntity entry) {
        entry.setIsActive(false);
        entry.setDeletedAt(Instant.now());
        jpaRepository.save(entry);
    }
}
