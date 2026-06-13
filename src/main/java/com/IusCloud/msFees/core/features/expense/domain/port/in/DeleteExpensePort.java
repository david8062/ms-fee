package com.IusCloud.msFees.core.features.expense.domain.port.in;

import java.util.UUID;

public interface DeleteExpensePort {
    void execute(UUID id, UUID tenantId);
}
