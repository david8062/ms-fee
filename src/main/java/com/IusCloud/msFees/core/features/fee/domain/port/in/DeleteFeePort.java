package com.IusCloud.msFees.core.features.fee.domain.port.in;

import java.util.UUID;

public interface DeleteFeePort {
    void execute(UUID id, UUID tenantId);
}
