package com.IusCloud.msFees.core.features.service.application.usecase;

import com.IusCloud.msFees.core.features.service.domain.model.ServiceEntity;
import com.IusCloud.msFees.core.features.service.domain.port.in.DeleteServicePort;
import com.IusCloud.msFees.core.features.service.domain.port.out.ServiceRepository;
import com.IusCloud.msFees.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteServiceUseCase implements DeleteServicePort {

    private final ServiceRepository serviceRepository;

    @Transactional
    @Override
    public void execute(UUID id, UUID tenantId) {
        ServiceEntity entity = serviceRepository.findById(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Service", id));
        serviceRepository.delete(entity);
    }
}
