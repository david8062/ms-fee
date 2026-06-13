package com.IusCloud.msFees.core.features.service.application.dto;

import com.IusCloud.msFees.shared.enums.PricingStrategyEnum;
import com.IusCloud.msFees.shared.enums.ServiceCategoryEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ServiceResponseDTO(
        UUID id,
        UUID tenantId,
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        ServiceCategoryEnum category,
        PricingStrategyEnum pricingStrategy,
        BigDecimal baseAmount,
        BigDecimal percentageAmount,
        String currency,
        Integer durationMinutes,
        Boolean isFree,
        Boolean isPublic,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {}
