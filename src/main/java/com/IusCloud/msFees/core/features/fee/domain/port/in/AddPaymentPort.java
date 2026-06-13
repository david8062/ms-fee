package com.IusCloud.msFees.core.features.fee.domain.port.in;

import com.IusCloud.msFees.core.features.fee.application.dto.PaymentRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;

import java.util.UUID;

public interface AddPaymentPort {
    PaymentResponseDTO execute(UUID feeId, PaymentRequestDTO request, UUID tenantId);
}
