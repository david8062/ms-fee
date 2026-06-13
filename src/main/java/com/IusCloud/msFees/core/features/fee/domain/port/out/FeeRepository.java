package com.IusCloud.msFees.core.features.fee.domain.port.out;

import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FeeRepository {
    FeeEntity save(FeeEntity fee);
    Optional<FeeEntity> findById(UUID id, UUID tenantId);
    Page<FeeEntity> findAll(UUID tenantId, UUID clientId, UUID caseId, UUID assignedUserId, Pageable pageable);
    void delete(FeeEntity fee);
}
