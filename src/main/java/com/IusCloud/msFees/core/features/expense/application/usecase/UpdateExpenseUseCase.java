package com.IusCloud.msFees.core.features.expense.application.usecase;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseRequestDTO;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import com.IusCloud.msFees.core.features.expense.application.mapper.ExpenseMapper;
import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import com.IusCloud.msFees.core.features.expense.domain.port.in.UpdateExpensePort;
import com.IusCloud.msFees.core.features.expense.domain.port.out.ExpenseRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateExpenseUseCase implements UpdateExpensePort {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Transactional
    @Override
    public ExpenseResponseDTO execute(UUID id, ExpenseRequestDTO request, UUID tenantId) {
        ExpenseEntity entity = expenseRepository.findById(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", id));
        expenseMapper.updateEntityFromDto(request, entity);
        return expenseMapper.toResponse(expenseRepository.save(entity));
    }
}
