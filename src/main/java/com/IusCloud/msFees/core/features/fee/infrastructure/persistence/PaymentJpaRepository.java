package com.IusCloud.msFees.core.features.fee.infrastructure.persistence;

import com.IusCloud.msFees.core.base.BaseRepository;
import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentJpaRepository extends BaseRepository<PaymentEntity, UUID> {

    List<PaymentEntity> findByFeeIdAndDeletedAtIsNull(UUID feeId);
}
