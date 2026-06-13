package com.IusCloud.msFees.core.features.fee.application.dto;

import com.IusCloud.msFees.shared.enums.PaymentMethodEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        UUID feeId,
        BigDecimal amount,
        LocalDate paymentDate,
        PaymentMethodEnum paymentMethod,
        String reference,
        String attachmentUrl,
        String notes,
        Instant createdAt
) {}
