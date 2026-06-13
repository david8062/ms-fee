package com.IusCloud.msFees.core.features.service.application.dto;

import com.IusCloud.msFees.shared.enums.PricingStrategyEnum;
import com.IusCloud.msFees.shared.enums.ServiceCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServiceRequestDTO(
        @NotBlank String name,
        String slug,
        String shortDescription,
        String fullDescription,
        ServiceCategoryEnum category,
        @NotNull PricingStrategyEnum pricingStrategy,
        BigDecimal baseAmount,
        BigDecimal percentageAmount,
        String currency,
        Integer durationMinutes,
        Boolean isFree,
        Boolean isPublic
) {}
