package com.IusCloud.msFees.core.features.expense.application.usecase;

import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import com.IusCloud.msFees.core.features.expense.domain.port.in.DeleteExpensePort;
import com.IusCloud.msFees.core.features.expense.domain.port.out.ExpenseRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteExpenseUseCase implements DeleteExpensePort {

    private final ExpenseRepository expenseRepository;

    @Transactional
    @Override
    public void execute(UUID id, UUID tenantId) {
        ExpenseEntity entity = expenseRepository.findById(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
        expenseRepository.delete(entity);
    }
}
