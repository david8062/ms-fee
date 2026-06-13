package com.IusCloud.msFees.core.features.fee.application.mapper;

import com.IusCloud.msFees.config.mapper.BaseEntityMapperConfig;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;
import com.IusCloud.msFees.core.features.fee.domain.model.FeeEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", config = BaseEntityMapperConfig.class)
public interface FeeMapper {

    @Mappings({
            @Mapping(target = "id",          ignore = true),
            @Mapping(target = "createdAt",   ignore = true),
            @Mapping(target = "updatedAt",   ignore = true),
            @Mapping(target = "deletedAt",   ignore = true),
            @Mapping(target = "isActive",    ignore = true),
            @Mapping(target = "tenantId",    source = "tenantId"),
            @Mapping(target = "status",      expression = "java(com.IusCloud.msFees.shared.enums.FeeStatusEnum.PENDING)"),
            @Mapping(target = "payments",    ignore = true),
            @Mapping(target = "timeEntries", ignore = true),
            @Mapping(target = "currency",    expression = "java(dto.currency() != null ? dto.currency() : \"COP\")")
    })
    FeeEntity toEntity(FeeRequestDTO dto, UUID tenantId);

    @Mappings({
            @Mapping(target = "totalPaid", expression = "java(computeTotalPaid(entity))"),
            @Mapping(target = "balance",   expression = "java(computeBalance(entity))")
    })
    FeeResponseDTO toResponse(FeeEntity entity);

    @Mappings({
            @Mapping(target = "id",          ignore = true),
            @Mapping(target = "createdAt",   ignore = true),
            @Mapping(target = "updatedAt",   ignore = true),
            @Mapping(target = "deletedAt",   ignore = true),
            @Mapping(target = "isActive",    ignore = true),
            @Mapping(target = "tenantId",    ignore = true),
            @Mapping(target = "status",      ignore = true),
            @Mapping(target = "payments",    ignore = true),
            @Mapping(target = "timeEntries", ignore = true)
    })
    void updateEntityFromDto(FeeRequestDTO dto, @MappingTarget FeeEntity entity);

    @Mapping(target = "feeId", expression = "java(payment.getFee().getId())")
    PaymentResponseDTO toPaymentResponse(PaymentEntity payment);

    List<PaymentResponseDTO> toPaymentResponses(List<PaymentEntity> payments);

    @Mapping(target = "feeId", expression = "java(entry.getFee().getId())")
    TimeEntryResponseDTO toTimeEntryResponse(TimeEntryEntity entry);

    List<TimeEntryResponseDTO> toTimeEntryResponses(List<TimeEntryEntity> entries);

    default BigDecimal computeTotalPaid(FeeEntity entity) {
        if (entity.getPayments() == null) return BigDecimal.ZERO;
        return entity.getPayments().stream()
                .filter(p -> Boolean.TRUE.equals(p.getIsActive()))
                .map(PaymentEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    default BigDecimal computeBalance(FeeEntity entity) {
        return entity.getAgreedAmount().subtract(computeTotalPaid(entity));
    }
}
