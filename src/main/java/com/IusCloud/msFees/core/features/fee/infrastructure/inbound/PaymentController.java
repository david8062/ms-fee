package com.IusCloud.msFees.core.features.fee.infrastructure.inbound;

import com.IusCloud.msFees.core.features.fee.application.dto.PaymentRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;
import com.IusCloud.msFees.core.features.fee.domain.port.in.AddPaymentPort;
import com.IusCloud.msFees.core.features.fee.domain.port.in.DeletePaymentPort;
import com.IusCloud.msFees.core.features.fee.domain.port.in.GetPaymentsByFeePort;
import com.IusCloud.msFees.shared.responses.ApiResponse;
import com.IusCloud.msFees.shared.responses.ListResponse;
import com.IusCloud.msFees.shared.responses.ResponseUtil;
import com.IusCloud.msFees.shared.tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fees/{feeId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final AddPaymentPort addPaymentUseCase;
    private final GetPaymentsByFeePort getPaymentsByFeeUseCase;
    private final DeletePaymentPort deletePaymentUseCase;

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> add(
            @PathVariable UUID feeId,
            @RequestBody @Valid PaymentRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.created(addPaymentUseCase.execute(feeId, request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<ListResponse<PaymentResponseDTO>> getAll(@PathVariable UUID feeId) {
        UUID tenantId = TenantContext.getTenantId();
        List<PaymentResponseDTO> payments = getPaymentsByFeeUseCase.execute(feeId, tenantId);
        return ResponseUtil.list(payments);
    }

    @PreAuthorize("hasAuthority('FEES:DELETE') or hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> delete(@PathVariable UUID feeId, @PathVariable UUID paymentId) {
        UUID tenantId = TenantContext.getTenantId();
        deletePaymentUseCase.execute(paymentId, feeId, tenantId);
        return ResponseUtil.noContent();
    }
}
