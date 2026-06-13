package com.IusCloud.msFees.core.features.expense.domain.port.in;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;

import java.util.UUID;

public interface GetExpenseByIdPort {
    ExpenseResponseDTO execute(UUID id, UUID tenantId);
}
