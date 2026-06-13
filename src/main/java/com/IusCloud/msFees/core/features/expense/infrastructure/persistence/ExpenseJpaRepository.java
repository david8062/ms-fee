package com.IusCloud.msFees.core.features.expense.infrastructure.persistence;

import com.IusCloud.msFees.core.base.BaseRepository;
import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseJpaRepository extends BaseRepository<ExpenseEntity, UUID> {

    Optional<ExpenseEntity> findByIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID tenantId);

    @Query("SELECT e FROM ExpenseEntity e WHERE e.tenantId = :tenantId " +
           "AND (:caseId IS NULL OR e.caseId = :caseId) " +
           "AND e.deletedAt IS NULL")
    Page<ExpenseEntity> findAllActive(
            @Param("tenantId") UUID tenantId,
            @Param("caseId") UUID caseId,
            Pageable pageable);
}
