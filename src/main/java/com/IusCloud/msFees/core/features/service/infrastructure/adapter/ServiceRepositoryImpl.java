package com.IusCloud.msFees.core.features.service.infrastructure.adapter;

import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import com.IusCloud.msFees.core.features.service.domain.port.out.ServiceRepository;
import com.IusCloud.msFees.core.features.service.infrastructure.persistence.ServiceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceJpaRepository jpaRepository;

    @Override
    public ServiceEntity save(ServiceEntity service) {
        return jpaRepository.save(service);
    }

    @Override
    public Optional<ServiceEntity> findById(UUID id, UUID tenantId) {
        return jpaRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, tenantId);
    }

    @Override
    public Page<ServiceEntity> findAll(UUID tenantId, Boolean isPublic, Pageable pageable) {
        return jpaRepository.findAllActive(tenantId, isPublic, pageable);
    }

    @Override
    public boolean existsByNameAndTenantId(String name, UUID tenantId) {
        return jpaRepository.existsByNameAndTenantIdAndDeletedAtIsNull(name, tenantId);
    }

    @Override
    public void delete(ServiceEntity service) {
        service.setIsActive(false);
        service.setDeletedAt(Instant.now());
        jpaRepository.save(service);
    }
}
