package com.IusCloud.msFees.core.features.service.application.usecase;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import com.IusCloud.msFees.core.features.service.application.mapper.ServiceMapper;
import com.IusCloud.msFees.core.features.service.domain.port.in.GetServiceByIdPort;
import com.IusCloud.msFees.core.features.service.domain.port.out.ServiceRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetServiceByIdUseCase implements GetServiceByIdPort {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public ServiceResponseDTO execute(UUID id, UUID tenantId) {
        return serviceRepository.findById(id, tenantId)
                .map(serviceMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Service", id));
    }
}
