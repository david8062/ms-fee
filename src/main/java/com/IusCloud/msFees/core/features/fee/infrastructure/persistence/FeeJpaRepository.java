package com.IusCloud.msFees.core.features.fee.infrastructure.persistence;

import com.IusCloud.msFees.core.base.BaseRepository;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeeJpaRepository extends BaseRepository<FeeEntity, UUID> {

    Optional<FeeEntity> findByIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID tenantId);

    @Query("SELECT f FROM FeeEntity f WHERE f.tenantId = :tenantId " +
           "AND (:clientId IS NULL OR f.clientId = :clientId) " +
           "AND (:caseId IS NULL OR f.caseId = :caseId) " +
           "AND (:assignedUserId IS NULL OR f.assignedUserId = :assignedUserId) " +
           "AND f.deletedAt IS NULL")
    Page<FeeEntity> findAllActive(
            @Param("tenantId") UUID tenantId,
            @Param("clientId") UUID clientId,
            @Param("caseId") UUID caseId,
            @Param("assignedUserId") UUID assignedUserId,
            Pageable pageable);
}
