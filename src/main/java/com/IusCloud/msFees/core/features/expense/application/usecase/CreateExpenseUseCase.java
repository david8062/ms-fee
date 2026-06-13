package com.IusCloud.msFees.core.features.expense.application.usecase;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseRequestDTO;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import com.IusCloud.msFees.core.features.expense.application.mapper.ExpenseMapper;
import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import com.IusCloud.msFees.core.features.expense.domain.port.in.CreateExpensePort;
import com.IusCloud.msFees.core.features.expense.domain.port.out.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateExpenseUseCase implements CreateExpensePort {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    @Transactional
    @Override
    public ExpenseResponseDTO execute(ExpenseRequestDTO request, UUID tenantId) {
        ExpenseEntity entity = expenseMapper.toEntity(request, tenantId);
        return expenseMapper.toResponse(expenseRepository.save(entity));
    }
}
