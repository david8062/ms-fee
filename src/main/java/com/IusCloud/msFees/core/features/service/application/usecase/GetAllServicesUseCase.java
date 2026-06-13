package com.IusCloud.msFees.core.features.service.application.usecase;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import com.IusCloud.msFees.core.features.service.application.mapper.ServiceMapper;
import com.IusCloud.msFees.core.features.service.domain.port.in.GetAllServicesPort;
import com.IusCloud.msFees.core.features.service.domain.port.out.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAllServicesUseCase implements GetAllServicesPort {

    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public Page<ServiceResponseDTO> execute(UUID tenantId, Boolean isPublic, Pageable pageable) {
        return serviceRepository.findAll(tenantId, isPublic, pageable)
                .map(serviceMapper::toResponse);
    }
}
