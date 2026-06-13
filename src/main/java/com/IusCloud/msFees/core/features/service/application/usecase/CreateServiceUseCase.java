package com.IusCloud.msFees.core.features.service.application.usecase;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceRequestDTO;
import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import com.IusCloud.msFees.core.features.service.application.mapper.ServiceMapper;
import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import com.IusCloud.msFees.core.features.service.domain.port.in.CreateServicePort;
import com.IusCloud.msFees.core.features.service.domain.port.out.ServiceRepository;
import com.IusCloud.msFees.shared.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateServiceUseCase implements CreateServicePort {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Transactional
    @Override
    public ServiceResponseDTO execute(ServiceRequestDTO request, UUID tenantId) {
        if (serviceRepository.existsByNameAndTenantId(request.name(), tenantId)) {
            throw new BusinessException("Ya existe un servicio con el nombre '" + request.name() + "'");
        }
        ServiceEntity entity = serviceMapper.toEntity(request, tenantId);
        return serviceMapper.toResponse(serviceRepository.save(entity));
    }
}
