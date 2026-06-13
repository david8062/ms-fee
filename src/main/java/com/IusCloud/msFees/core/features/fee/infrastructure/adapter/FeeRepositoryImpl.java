package com.IusCloud.msFees.core.features.fee.infrastructure.adapter;

import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.infrastructure.persistence.FeeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeeRepositoryImpl implements FeeRepository {

    private final FeeJpaRepository jpaRepository;

    @Override
    public FeeEntity save(FeeEntity fee) {
        return jpaRepository.save(fee);
    }

    @Override
    public Optional<FeeEntity> findById(UUID id, UUID tenantId) {
        return jpaRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, tenantId);
    }

    @Override
    public Page<FeeEntity> findAll(UUID tenantId, UUID clientId, UUID caseId, UUID assignedUserId, Pageable pageable) {
        return jpaRepository.findAllActive(tenantId, clientId, caseId, assignedUserId, pageable);
    }

    @Override
    public void delete(FeeEntity fee) {
        fee.setIsActive(false);
        fee.setDeletedAt(Instant.now());
        jpaRepository.save(fee);
    }
}
