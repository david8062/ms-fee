package com.IusCloud.msFees.core.features.expense.infrastructure.inbound;

import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseRequestDTO;
import com.IusCloud.msFees.core.features.expense.application.dto.ExpenseResponseDTO;
import com.IusCloud.msFees.core.features.expense.domain.port.in.*;
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
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final CreateExpensePort createExpenseUseCase;
    private final UpdateExpensePort updateExpenseUseCase;
    private final GetExpenseByIdPort getExpenseByIdUseCase;
    private final GetAllExpensesPort getAllExpensesUseCase;
    private final DeleteExpensePort deleteExpenseUseCase;

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponseDTO>> create(
            @RequestBody @Valid ExpenseRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.created(createExpenseUseCase.execute(request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:WRITE') or hasRole('ADMINISTRATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDTO>> update(
            @PathVariable UUID id,
            @RequestBody @Valid ExpenseRequestDTO request) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.ok(updateExpenseUseCase.execute(id, request, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponseDTO>> getById(@PathVariable UUID id) {
        UUID tenantId = TenantContext.getTenantId();
        return ResponseUtil.ok(getExpenseByIdUseCase.execute(id, tenantId));
    }

    @PreAuthorize("hasAuthority('FEES:READ') or hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<PagedResponse<ExpenseResponseDTO>> getAll(
            @RequestParam(required = false) UUID caseId,
            @PageableDefault(size = 20) Pageable pageable) {
        UUID tenantId = TenantContext.getTenantId();
        Page<ExpenseResponseDTO> page = getAllExpensesUseCase.execute(tenantId, caseId, pageable);
        return ResponseUtil.paged(page);
    }

    @PreAuthorize("hasAuthority('FEES:DELETE') or hasRole('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UUID tenantId = TenantContext.getTenantId();
        deleteExpenseUseCase.execute(id, tenantId);
        return ResponseUtil.noContent();
    }
}
