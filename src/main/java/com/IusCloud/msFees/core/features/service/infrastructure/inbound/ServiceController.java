package com.IusCloud.msFees.core.features.service.infrastructure.inbound;

import com.IusCloud.msFees.core.features.service.application.dto.ServiceRequestDTO;
import com.IusCloud.msFees.core.features.service.application.dto.ServiceResponseDTO;
import com.IusCloud.msFees.core.features.service.domain.port.in.*;
import com.IusCloud.msFees.shared.responses.ApiResponse;
import com.IusCloud.msFees.shared.responses.PagedResponse;
import com.IusCloud.msFees.shared.responses.ResponseUtil;
import com.IusCloud.msFees.shared.tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final CreateServicePort createServiceUseCase;
    private final UpdateServicePort updateServiceUseCase;
    private final GetServiceByIdPort getServiceByIdUseCase;
    private final GetAllServicesPort getAllServicesUseCase;
    private final DeleteServicePort deleteServiceUseCase;

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<ServiceResponseDTO>> create(
            @RequestBody @Valid ServiceRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.created(createServiceUseCase.execute(request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponseDTO>> update(
            @PathVariable UUID id,
            @RequestBody @Valid ServiceRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.ok(updateServiceUseCase.execute(id, request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponseDTO>> getById(@PathVariable UUID id) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.ok(getServiceByIdUseCase.execute(id, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<PagedResponse<ServiceResponseDTO>> getAll(
            @RequestParam(required = false) Boolean isPublic,
            @PageableDefault(size = 20) Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        Page<ServiceResponseDTO> page = getAllServicesUseCase.execute(tenantId, isPublic, pageable);
        return ResponseUtil.paged(page);
    }

    @PreAuthorize("hasAuthority('FEES:DELETE') or hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UUID tenantId = TenantContext.getTenantId();
        deleteServiceUseCase.execute(id, tenantId);
        return ResponseUtil.noContent();
    }
}
