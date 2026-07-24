package com.IusCloud.msFees.core.features.fee.application.dto;

import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import com.IusCloud.msFees.shared.enums.FeeTypeEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FeeResponseDTO(
        UUID id,
        UUID tenantId,
        UUID clientId,
        UUID caseId,
        UUID serviceId,
        UUID assignedUserId,
        String description,
        FeeTypeEnum feeType,
        BigDecimal agreedAmount,
        BigDecimal totalPaid,
        BigDecimal balance,
        String currency,
        LocalDate dueDate,
        FeeStatusEnum status,
        String notes,
        List<PaymentResponseDTO> payments,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {}
