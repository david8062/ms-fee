package com.IusCloud.msFees.core.features.service.domain.model;

import com.IusCloud.msFees.core.base.BaseModel;
import com.IusCloud.msFees.shared.enums.PricingStrategyEnum;
import com.IusCloud.msFees.shared.enums.ServiceCategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "services")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity extends BaseModel {

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ServiceCategoryEnum category;

    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_strategy", nullable = false)
    private PricingStrategyEnum pricingStrategy;

    @Column(name = "base_amount", precision = 19, scale = 2)
    private BigDecimal baseAmount;

    @Column(name = "percentage_amount", precision = 5, scale = 2)
    private BigDecimal percentageAmount;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency = "COP";

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "is_free", nullable = false)
    private Boolean isFree = false;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = false;
}
