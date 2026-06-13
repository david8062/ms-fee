package com.IusCloud.msFees.core.features.expense.infrastructure.adapter;

import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import com.IusCloud.msFees.core.features.expense.domain.port.out.ExpenseRepository;
import com.IusCloud.msFees.core.features.expense.infrastructure.persistence.ExpenseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final ExpenseJpaRepository jpaRepository;

    @Override
    public ExpenseEntity save(ExpenseEntity expense) {
        return jpaRepository.save(expense);
    }

    @Override
    public Optional<ExpenseEntity> findById(UUID id, UUID tenantId) {
        return jpaRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, tenantId);
    }

    @Override
    public Page<ExpenseEntity> findAll(UUID tenantId, UUID caseId, Pageable pageable) {
        return jpaRepository.findAllActive(tenantId, caseId, pageable);
    }

    @Override
    public void delete(ExpenseEntity expense) {
        expense.setIsActive(false);
        expense.setDeletedAt(Instant.now());
        jpaRepository.save(expense);
    }
}
