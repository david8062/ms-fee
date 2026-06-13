package com.IusCloud.msFees.core.features.service.domain.port.out;

import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository {
    ServiceEntity save(ServiceEntity service);
    Optional<ServiceEntity> findById(UUID id, UUID tenantId);
    Page<ServiceEntity> findAll(UUID tenantId, Boolean isPublic, Pageable pageable);
    boolean existsByNameAndTenantId(String name, UUID tenantId);
    void delete(ServiceEntity service);
}
