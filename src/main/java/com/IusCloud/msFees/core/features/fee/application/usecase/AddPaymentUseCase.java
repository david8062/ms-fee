package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.PaymentRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.PaymentMapper;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.in.AddPaymentPort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.PaymentRepository;
import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import com.IusCloud.msFees.shared.exceptions.BusinessException;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddPaymentUseCase implements AddPaymentPort {

    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public PaymentResponseDTO execute(UUID feeId, PaymentRequestDTO request, UUID tenantId) {
        FeeEntity fee = feeRepository.findById(feeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", feeId));

        if (fee.getStatus() == FeeStatusEnum.CANCELLED) {
            throw new BusinessException("No se puede registrar un pago en un honorario cancelado");
        }

        PaymentEntity payment = paymentMapper.toEntity(request);
        payment.setFee(fee);
        PaymentEntity saved = paymentRepository.save(payment);

        recalculateStatus(fee, tenantId);

        return paymentMapper.toResponse(saved);
    }

    private void recalculateStatus(FeeEntity fee, UUID tenantId) {
        BigDecimal totalPaid = paymentRepository.findByFeeId(fee.getId()).stream()
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
