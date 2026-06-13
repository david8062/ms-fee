package com.IusCloud.msFees.core.features.fee.application.dto;

import com.IusCloud.msFees.shared.enums.PaymentMethodEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentRequestDTO(
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate paymentDate,
        @NotNull PaymentMethodEnum paymentMethod,
        String reference,
        String attachmentUrl,
        String notes
) {}
