package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.DeletePaymentPort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.PaymentRepository;
import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePaymentUseCase implements DeletePaymentPort {

    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public void execute(UUID paymentId, UUID feeId, UUID tenantId) {
        feeRepository.findById(feeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", feeId));

        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));

        paymentRepository.delete(payment);
        recalculateStatus(feeId, tenantId);
    }

    private void recalculateStatus(UUID feeId, UUID tenantId) {
        FeeEntity fee = feeRepository.findById(feeId, tenantId).orElseThrow();

        BigDecimal totalPaid = paymentRepository.findByFeeId(feeId).stream()
                .filter(p -> Boolean.TRUE.equals(p.getIsActive()))
                .map(PaymentEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int cmp = totalPaid.compareTo(fee.getAgreedAmount());
        if (cmp >= 0) {
            fee.setStatus(FeeStatusEnum.PAID);
        } else if (totalPaid.compareTo(BigDecimal.ZERO) > 0) {
            fee.setStatus(FeeStatusEnum.PARTIAL);
        } else {
            fee.setStatus(FeeStatusEnum.PENDING);
        }
        feeRepository.save(fee);
    }
}
