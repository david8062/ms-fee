package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.PaymentRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.PaymentMapper;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.PaymentRepository;
import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import com.IusCloud.msFees.shared.exceptions.BusinessException;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddPaymentUseCaseTest {

    @Mock FeeRepository feeRepository;
    @Mock PaymentRepository paymentRepository;
    @Mock PaymentMapper paymentMapper;

    @InjectMocks AddPaymentUseCase useCase;

    private final UUID feeId    = UUID.randomUUID();
    private final UUID tenantId = UUID.randomUUID();

    /** Valores irrelevantes: el mapper está mockeado, solo importa el flujo. */
    private static final PaymentRequestDTO REQ =
            new PaymentRequestDTO(null, null, null, null, null, null);

    private FeeEntity fee;

    @BeforeEach
    void setUp() {
        fee = new FeeEntity();
        fee.setTenantId(tenantId);
        fee.setAgreedAmount(new BigDecimal("1000.00"));
        fee.setStatus(FeeStatusEnum.PENDING);
    }

    private PaymentEntity payment(String amount, boolean active) {
        PaymentEntity p = new PaymentEntity();
        p.setAmount(new BigDecimal(amount));
        p.setIsActive(active);
        return p;
    }

    /** Configura el camino feliz: fee existe, mapper y save responden, y findByFeeId devuelve `afterSave`. */
    private void stubHappyPath(List<PaymentEntity> afterSave) {
        PaymentEntity mapped = new PaymentEntity();
        when(feeRepository.findById(feeId, tenantId)).thenReturn(Optional.of(fee));
        when(paymentMapper.toEntity(any(PaymentRequestDTO.class))).thenReturn(mapped);
        when(paymentRepository.save(mapped)).thenReturn(mapped);
        when(paymentRepository.findByFeeId(any())).thenReturn(afterSave);
        // toResponse devuelve null sin stub; el test verifica el estado del fee, no la respuesta.
    }

    private FeeStatusEnum capturedSavedStatus() {
        ArgumentCaptor<FeeEntity> captor = ArgumentCaptor.forClass(FeeEntity.class);
        verify(feeRepository).save(captor.capture());
        return captor.getValue().getStatus();
    }

    // ── errores ────────────────────────────────────────────────────────────────

    @Test
    void feeInexistente_lanzaNotFound() {
        when(feeRepository.findById(feeId, tenantId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(feeId, REQ, tenantId))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void feeCancelado_lanzaBusinessYNoGuardaPago() {
        fee.setStatus(FeeStatusEnum.CANCELLED);
        when(feeRepository.findById(feeId, tenantId)).thenReturn(Optional.of(fee));

        assertThatThrownBy(() -> useCase.execute(feeId, REQ, tenantId))
                .isInstanceOf(BusinessException.class);
        verify(paymentRepository, never()).save(any());
    }

    // ── recálculo de estado ───────────────────────────────────────────────────

    @Test
    void pagoParcial_dejaFeeEnPartial() {
        stubHappyPath(List.of(payment("400.00", true)));

        useCase.execute(feeId, REQ, tenantId);

        assertThat(capturedSavedStatus()).isEqualTo(FeeStatusEnum.PARTIAL);
    }

    @Test
    void pagoExacto_dejaFeeEnPaid() {
        stubHappyPath(List.of(payment("1000.00", true)));

        useCase.execute(feeId, REQ, tenantId);

        assertThat(capturedSavedStatus()).isEqualTo(FeeStatusEnum.PAID);
    }

    @Test
    void sobrepago_dejaFeeEnPaid() {
        stubHappyPath(List.of(payment("1200.00", true)));

        useCase.execute(feeId, REQ, tenantId);

        assertThat(capturedSavedStatus()).isEqualTo(FeeStatusEnum.PAID);
    }

    @Test
    void variosPagosQueSumanElTotal_dejaFeeEnPaid() {
        stubHappyPath(List.of(payment("600.00", true), payment("400.00", true)));

        useCase.execute(feeId, REQ, tenantId);

        assertThat(capturedSavedStatus()).isEqualTo(FeeStatusEnum.PAID);
    }

    @Test
    void pagosInactivosNoCuentan_paraElTotal() {
        // 800 activo + 500 inactivo = 800 efectivos < 1000 -> PARTIAL (no PAID)
        stubHappyPath(List.of(payment("800.00", true), payment("500.00", false)));

        useCase.execute(feeId, REQ, tenantId);

        assertThat(capturedSavedStatus()).isEqualTo(FeeStatusEnum.PARTIAL);
    }

    @Test
    void sinPagosEfectivos_quedaPending() {
        stubHappyPath(List.of(payment("300.00", false)));

        useCase.execute(feeId, REQ, tenantId);

        assertThat(capturedSavedStatus()).isEqualTo(FeeStatusEnum.PENDING);
    }
}
