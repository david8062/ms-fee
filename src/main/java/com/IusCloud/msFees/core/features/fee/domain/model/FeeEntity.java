package com.IusCloud.msFees.core.features.fee.domain.model;

import com.IusCloud.msFees.core.base.BaseModel;
import com.IusCloud.msFees.shared.enums.FeeStatusEnum;
import com.IusCloud.msFees.shared.enums.FeeTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeeEntity extends BaseModel {

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "case_id")
    private UUID caseId;

    @Column(name = "service_id")
    private UUID serviceId;

    @Column(name = "assigned_user_id", nullable = false)
    private UUID assignedUserId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type", nullable = false)
    private FeeTypeEnum feeType;

    @Column(name = "agreed_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal agreedAmount;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency = "COP";

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FeeStatusEnum status = FeeStatusEnum.PENDING;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentEntity> payments = new ArrayList<>();
}
