package com.IusCloud.msFees.core.features.service.infrastructure.persistence;

import com.IusCloud.msFees.core.base.BaseRepository;
import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceJpaRepository extends BaseRepository<ServiceEntity, UUID> {

    Optional<ServiceEntity> findByIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID tenantId);

    boolean existsByNameAndTenantIdAndDeletedAtIsNull(String name, UUID tenantId);

    @Query("SELECT s FROM ServiceEntity s WHERE s.tenantId = :tenantId " +
           "AND (:isPublic IS NULL OR s.isPublic = :isPublic) " +
           "AND s.deletedAt IS NULL")
    Page<ServiceEntity> findAllActive(
            @Param("tenantId") UUID tenantId,
            @Param("isPublic") Boolean isPublic,
            Pageable pageable);
}
