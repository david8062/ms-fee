package com.IusCloud.msFees.core.features.expense.domain.port.in;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseRequestDTO;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;

import java.util.UUID;

public interface UpdateExpensePort {
    ExpenseResponseDTO execute(UUID id, ExpenseRequestDTO request, UUID tenantId);
}
