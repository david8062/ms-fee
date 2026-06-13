package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.PaymentMapper;
import com.IusCloud.msFees.core.features.fee.domain.port.in.GetPaymentsByFeePort;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.PaymentRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPaymentsByFeeUseCase implements GetPaymentsByFeePort {

    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponseDTO> execute(UUID feeId, UUID tenantId) {
        feeRepository.findById(feeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Fee", feeId));
        return paymentRepository.findByFeeId(feeId).stream()
                .map(paymentMapper::toResponse)
                .toList();
    }
}
