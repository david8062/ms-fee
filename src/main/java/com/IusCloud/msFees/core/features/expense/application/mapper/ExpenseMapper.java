package com.IusCloud.msFees.core.features.expense.application.mapper;

import com.IusCloud.msFees.config.mapper.BaseEntityMapperConfig;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseRequestDTO;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import com.IusCloud.msFees.core.features.expense.domain.model.ExpenseEntity;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = "spring", config = BaseEntityMapperConfig.class)
public interface ExpenseMapper {

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "isActive",  ignore = true),
            @Mapping(target = "tenantId",  source = "tenantId")
    })
    ExpenseEntity toEntity(ExpenseRequestDTO dto, UUID tenantId);

    ExpenseResponseDTO toResponse(ExpenseEntity entity);

    @Mappings({
            @Mapping(target = "id",        ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "isActive",  ignore = true),
            @Mapping(target = "tenantId",  ignore = true)
    })
    void updateEntityFromDto(ExpenseRequestDTO dto, @MappingTarget ExpenseEntity entity);
}
