package com.IusCloud.msFees.core.features.expense.domain.port.in;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GetAllExpensesPort {
    Page<ExpenseResponseDTO> execute(UUID tenantId, UUID caseId, Pageable pageable);
}
