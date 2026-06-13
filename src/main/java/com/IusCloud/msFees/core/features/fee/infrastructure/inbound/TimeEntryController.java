package com.IusCloud.msFees.core.features.fee.infrastructure.inbound;

import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryRequestDTO;
import com.IusCloud.msFees.core.features.fee.application.dto.TimeEntryResponseDTO;
import com.IusCloud.msFees.core.features.fee.domain.port.in.AddTimeEntryPort;
import com.IusCloud.msFees.core.features.fee.domain.port.in.DeleteTimeEntryPort;
import com.IusCloud.msFees.core.features.fee.domain.port.in.GetTimeEntriesByFeePort;
import com.IusCloud.msFees.shared.responses.ApiResponse;
import com.IusCloud.msFees.shared.responses.ListResponse;
import com.IusCloud.msFees.shared.responses.ResponseUtil;
import com.IusCloud.msFees.shared.tenant.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/fees/{feeId}/time-entries")
@RequiredArgsConstructor
public class TimeEntryController {

    private final AddTimeEntryPort addTimeEntryUseCase;
    private final GetTimeEntriesByFeePort getTimeEntriesByFeeUseCase;
    private final DeleteTimeEntryPort deleteTimeEntryUseCase;

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<TimeEntryResponseDTO>> add(
            @PathVariable UUID feeId,
            @RequestBody @Valid TimeEntryRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.created(addTimeEntryUseCase.execute(feeId, request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<ListResponse<TimeEntryResponseDTO>> getAll(@PathVariable UUID feeId) {
        UUID tenantId = TenantContext.getTenantId();
        List<TimeEntryResponseDTO> entries = getTimeEntriesByFeeUseCase.execute(feeId, tenantId);
        return ResponseUtil.list(entries);
    }

    @PreAuthorize("hasAuthority('FEES:DELETE') or hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{entryId}")
    public ResponseEntity<Void> delete(@PathVariable UUID feeId, @PathVariable UUID entryId) {
        UUID tenantId = TenantContext.getTenantId();
        deleteTimeEntryUseCase.execute(entryId, feeId, tenantId);
        return ResponseUtil.noContent();
    }
}
