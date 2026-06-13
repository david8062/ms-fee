package com.IusCloud.msFees.core.features.fee.application.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TimeEntryResponseDTO(
        UUID id,
        UUID feeId,
        UUID userId,
        String description,
        int hours,
        int minutes,
        LocalDate workDate,
        Instant createdAt
) {}
