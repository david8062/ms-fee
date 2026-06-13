package com.IusCloud.msFees.core.features.fee.domain.port.in;

import java.util.UUID;

public interface DeleteTimeEntryPort {
    void execute(UUID entryId, UUID feeId, UUID tenantId);
}
