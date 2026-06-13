package com.IusCloud.msFees.core.features.expense.domain.port.out;

import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository {
    ExpenseEntity save(ExpenseEntity expense);
    Optional<ExpenseEntity> findById(UUID id, UUID tenantId);
    Page<ExpenseEntity> findAll(UUID tenantId, UUID caseId, Pageable pageable);
    void delete(ExpenseEntity expense);
}
