package com.IusCloud.msFees.core.features.fee.application.usecase;

import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.mapper.TimeEntryMapper;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import com.IusCloud.msFees.core.features.fee.domain.port.out.FeeRepository;
import com.IusCloud.msFees.core.features.fee.domain.port.out.TimeEntryRepository;
import com.IusCloud.msFees.shared.enums.FeeTypeEnum;
import com.IusCloud.msFees.shared.exceptions.BusinessException;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddTimeEntryUseCaseTest {

    @Mock FeeRepository feeRepository;
    @Mock TimeEntryRepository timeEntryRepository;
    @Mock TimeEntryMapper timeEntryMapper;

    @InjectMocks AddTimeEntryUseCase useCase;

    private final UUID feeId    = UUID.randomUUID();
    private final UUID tenantId = UUID.randomUUID();
    private static final TimeEntryRequestDTO REQ =
            new TimeEntryRequestDTO(UUID.randomUUID(), "Audiencia", 2, 30, LocalDate.now());

    private FeeEntity feeOfType(FeeTypeEnum type) {
        FeeEntity fee = new FeeEntity();
        fee.setTenantId(tenantId);
        fee.setFeeType(type);
        return fee;
    }

    @Test
    void feeInexistente_lanzaNotFound() {
        when(feeRepository.findById(feeId, tenantId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(feeId, REQ, tenantId))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(timeEntryRepository, never()).save(any());
    }

    @Test
    void feeNoHorario_rechazaRegistroDeTiempo() {
        when(feeRepository.findById(feeId, tenantId)).thenReturn(Optional.of(feeOfType(FeeTypeEnum.FIXED)));

        assertThatThrownBy(() -> useCase.execute(feeId, REQ, tenantId))
                .isInstanceOf(BusinessException.class);
        verify(timeEntryRepository, never()).save(any());
    }

    @Test
    void feeHorario_guardaElRegistroDeTiempo() {
        FeeEntity fee = feeOfType(FeeTypeEnum.HOURLY);
        TimeEntryEntity entry = new TimeEntryEntity();
        when(feeRepository.findById(feeId, tenantId)).thenReturn(Optional.of(fee));
        when(timeEntryMapper.toEntity(any(TimeEntryRequestDTO.class))).thenReturn(entry);
        when(timeEntryRepository.save(entry)).thenReturn(entry);

        useCase.execute(feeId, REQ, tenantId);

        verify(timeEntryRepository).save(entry);
    }
}
