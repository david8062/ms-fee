package com.IusCloud.msFees.core.features.service.application.usecase;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceRequestDTO;
import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import com.IusCloud.msFees.core.features.service.application.mapper.ServiceMapper;
import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import com.IusCloud.msFees.core.features.service.domain.port.in.UpdateServicePort;
import com.IusCloud.msFees.core.features.service.domain.port.out.ServiceRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateServiceUseCase implements UpdateServicePort {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Transactional
    @Override
    public ServiceResponseDTO execute(UUID id, ServiceRequestDTO request, UUID tenantId) {
        ServiceEntity entity = serviceRepository.findById(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Service", id));
        serviceMapper.updateEntityFromDto(request, entity);
        return serviceMapper.toResponse(serviceRepository.save(entity));
    }
}
