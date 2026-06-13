package com.IusCloud.msFees.core.features.fee.application.mapper;

import com.IusCloud.msFees.config.mapper.BaseEntityMapperConfig;
import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;
import com.IusCloud.msFees.core.features.fee.domain.model.TimeEntryEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", config = BaseEntityMapperConfig.class)
public interface TimeEntryMapper {

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "isActive",  ignore = true),
            @Mapping(target = "fee",       ignore = true)
    })
    TimeEntryEntity toEntity(TimeEntryRequestDTO dto);

    @Mapping(target = "feeId", expression = "java(entity.getFee().getId())")
    TimeEntryResponseDTO toResponse(TimeEntryEntity entity);
}
