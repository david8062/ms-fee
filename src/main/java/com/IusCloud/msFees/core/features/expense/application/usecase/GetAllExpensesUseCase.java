package com.IusCloud.msFees.core.features.expense.application.usecase;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import com.IusCloud.msFees.core.features.expense.application.mapper.ExpenseMapper;
import com.IusCloud.msFees.core.features.expense.domain.port.in.GetAllExpensesPort;
import com.IusCloud.msFees.core.features.expense.domain.port.out.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllExpensesUseCase implements GetAllExpensesPort {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public Page<ExpenseResponseDTO> execute(UUID tenantId, UUID caseId, Pageable pageable) {
        return expenseRepository.findAll(tenantId, caseId, pageable)
                .map(expenseMapper::toResponse);
    }
}
