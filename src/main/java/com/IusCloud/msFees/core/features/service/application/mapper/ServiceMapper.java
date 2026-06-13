package com.IusCloud.msFees.core.features.service.application.mapper;

import com.IusCloud.msFees.config.mapper.BaseEntityMapperConfig;
import com.IusCloud.msFees.core.features.service.application.dto.ServiceRequestDTO;
import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", config = BaseEntityMapperConfig.class)
public interface ServiceMapper {

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "isActive",  ignore = true),
            @Mapping(target = "tenantId",  source = "tenantId")
    })
    ServiceEntity toEntity(ServiceRequestDTO dto, UUID tenantId);

    ServiceResponseDTO toResponse(ServiceEntity entity);

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "isActive",  ignore = true),
            @Mapping(target = "tenantId",  ignore = true)
    })
    void updateEntityFromDto(ServiceRequestDTO dto, @MappingTarget ServiceEntity entity);
}
