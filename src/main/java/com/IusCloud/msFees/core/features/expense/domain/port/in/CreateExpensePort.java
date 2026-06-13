package com.IusCloud.msFees.core.features.expense.domain.port.in;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseRequestDTO;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;

import java.util.UUID;

public interface CreateExpensePort {
    ExpenseResponseDTO execute(ExpenseRequestDTO request, UUID tenantId);
}
