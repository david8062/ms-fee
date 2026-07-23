package com.IusCloud.msFees.core.features.fee.infrastructure.persistence;

import com.IusCloud.msFees.core.base.BaseRepository;
import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimeEntryJpaRepository extends BaseRepository<TimeEntryEntity, UUID> {

    List<TimeEntryEntity> findByFeeIdAndDeletedAtIsNull(UUID feeId);

    Optional<TimeEntryEntity> findByIdAndFeeIdAndDeletedAtIsNull(UUID id, UUID feeId);
}
