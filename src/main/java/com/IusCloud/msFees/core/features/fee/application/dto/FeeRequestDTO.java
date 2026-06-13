package com.IusCloud.msFees.core.features.fee.application.dto;

import com.IusCloud.msFees.shared.enums.FeeTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FeeRequestDTO(
        @NotNull UUID clientId,
        UUID caseId,
        UUID serviceId,
        @NotNull UUID assignedUserId,
        String description,
        @NotNull FeeTypeEnum feeType,
        @NotNull @Positive BigDecimal agreedAmount,
        String currency,
        LocalDate dueDate,
        String notes
) {}
