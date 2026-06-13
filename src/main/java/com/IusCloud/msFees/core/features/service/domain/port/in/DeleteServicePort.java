package com.IusCloud.msFees.core.features.service.domain.port.in;

import java.util.UUID;

public interface DeleteServicePort {
    void execute(UUID id, UUID tenantId);
}
