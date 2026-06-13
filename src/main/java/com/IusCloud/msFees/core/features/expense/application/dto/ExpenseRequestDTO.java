package com.IusCloud.msFees.core.features.expense.application.dto;

import com.IusCloud.msFees.shared.enums.ExpenseCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseRequestDTO(
        UUID caseId,
        @NotBlank String description,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate expenseDate,
        Boolean reimbursable,
        @NotNull ExpenseCategoryEnum category
) {}
