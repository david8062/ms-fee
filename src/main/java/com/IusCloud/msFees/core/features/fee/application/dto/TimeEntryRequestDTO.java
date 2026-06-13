package com.IusCloud.msFees.core.features.fee.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record TimeEntryRequestDTO(
        @NotNull UUID userId,
        String description,
        @Min(0) int hours,
        @Min(0) @Max(59) int minutes,
        @NotNull LocalDate workDate
) {}
