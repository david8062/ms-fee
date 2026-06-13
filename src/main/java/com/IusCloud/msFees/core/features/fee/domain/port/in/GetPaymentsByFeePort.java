package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface GetPaymentsByFeePort {
    List<PaymentResponseDTO> execute(UUID feeId, UUID tenantId);
}
