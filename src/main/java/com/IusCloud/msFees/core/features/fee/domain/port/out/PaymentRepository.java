package com.IusCloud.msFees.core.features.fee.domain.port.out;

import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    PaymentEntity save(PaymentEntity payment);
    Optional<PaymentEntity> findById(UUID id);
    List<PaymentEntity> findByFeeId(UUID feeId);
    void delete(PaymentEntity payment);
}
