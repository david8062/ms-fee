package com.IusCloud.msFees.core.features.fee.infrastructure.inbound;

import com.IusCloud.msFees.core.features.fee.application.dto.FeeRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.FeeResponseDTO;
import com.IusCloud.msFees.core.features.fee.domain.port.in.*;
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
@RequestMapping("/api/v1/fees")
@RequiredArgsConstructor
public class FeeController {

    private final CreateFeePort createFeeUseCase;
    private final UpdateFeePort updateFeeUseCase;
    private final GetFeeByIdPort getFeeByIdUseCase;
    private final GetAllFeesPort getAllFeesUseCase;
    private final DeleteFeePort deleteFeeUseCase;

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<FeeResponseDTO>> create(
            @RequestBody @Valid FeeRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.created(createFeeUseCase.execute(request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FeeResponseDTO>> update(
            @PathVariable UUID id,
            @RequestBody @Valid FeeRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.ok(updateFeeUseCase.execute(id, request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FeeResponseDTO>> getById(@PathVariable UUID id) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.ok(getFeeByIdUseCase.execute(id, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<PagedResponse<FeeResponseDTO>> getAll(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) UUID caseId,
            @RequestParam(required = false) UUID assignedUserId,
            @PageableDefault(size = 10) Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        Page<FeeResponseDTO> page = getAllFeesUseCase.execute(tenantId, clientId, caseId, assignedUserId, pageable);
        return ResponseUtil.paged(page);
    }

    @PreAuthorize("hasAuthority('FEES:DELETE') or hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UUID tenantId = TenantContext.getTenantId();
        deleteFeeUseCase.execute(id, tenantId);
        return ResponseUtil.noContent();
    }
}
