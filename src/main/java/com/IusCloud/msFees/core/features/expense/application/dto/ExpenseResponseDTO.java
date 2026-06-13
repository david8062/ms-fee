package com.IusCloud.msFees.core.features.expense.application.dto;

import com.IusCloud.msFees.shared.enums.ExpenseCategoryEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponseDTO(
        UUID id,
        UUID tenantId,
        UUID caseId,
        String description,
        BigDecimal amount,
        LocalDate expenseDate,
        Boolean reimbursable,
        ExpenseCategoryEnum category,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {}
