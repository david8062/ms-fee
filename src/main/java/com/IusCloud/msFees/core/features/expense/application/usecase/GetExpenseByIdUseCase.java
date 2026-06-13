package com.IusCloud.msFees.core.features.expense.application.usecase;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import com.IusCloud.msFees.core.features.expense.application.mapper.ExpenseMapper;
import com.IusCloud.msFees.core.features.expense.domain.port.in.GetExpenseByIdPort;
import com.IusCloud.msFees.core.features.expense.domain.port.out.ExpenseRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetExpenseByIdUseCase implements GetExpenseByIdPort {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Override
    public ExpenseResponseDTO execute(UUID id, UUID tenantId) {
        return expenseRepository.findById(id, tenantId)
                .map(expenseMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
    }
}
