package com.IusCloud.msFees.core.features.fee.infrastructure.adapter;

import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.out.PaymentRepository;
import com.IusCloud.msFees.core.features.fee.infrastructure.persistence.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public PaymentEntity save(PaymentEntity payment) {
        return jpaRepository.save(payment);
    }

    @Override
    public Optional<PaymentEntity> findByIdAndFeeId(UUID id, UUID feeId) {
        return jpaRepository.findByIdAndFeeIdAndDeletedAtIsNull(id, feeId);
    }

    @Override
    public List<PaymentEntity> findByFeeId(UUID feeId) {
        return jpaRepository.findByFeeIdAndDeletedAtIsNull(feeId);
    }

    @Override
    public void delete(PaymentEntity payment) {
        payment.setIsActive(false);
        payment.setDeletedAt(Instant.now());
        jpaRepository.save(payment);
    }
}
