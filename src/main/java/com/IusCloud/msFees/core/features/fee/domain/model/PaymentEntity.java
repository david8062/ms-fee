package com.IusCloud.msFees.core.features.fee.domain.model;

import com.IusCloud.msFees.core.base.BaseModel;
import com.IusCloud.msFees.shared.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_id", nullable = false)
    private FeeEntity fee;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Column(name = "reference")
    private String reference;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
