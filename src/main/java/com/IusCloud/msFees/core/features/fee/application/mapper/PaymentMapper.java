package com.IusCloud.msFees.core.features.fee.application.mapper;

import com.IusCloud.msFees.config.mapper.BaseEntityMapperConfig;
import com.IusCloud.msFees.core.features.fee.application.dto.PaymentRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.PaymentResponseDTO;
import com.IusCloud.msFees.core.features.fee.domain.model.PaymentEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", config = BaseEntityMapperConfig.class)
public interface PaymentMapper {

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "isActive",  ignore = true),
            @Mapping(target = "fee",       ignore = true)
    })
    PaymentEntity toEntity(PaymentRequestDTO dto);

    @Mapping(target = "feeId", expression = "java(entity.getFee().getId())")
    PaymentResponseDTO toResponse(PaymentEntity entity);
}
