package com.IusCloud.msFees.core.features.fee.domain.port.in;

import java.util.UUID;

public interface DeletePaymentPort {
    void execute(UUID paymentId, UUID feeId, UUID tenantId);
}
